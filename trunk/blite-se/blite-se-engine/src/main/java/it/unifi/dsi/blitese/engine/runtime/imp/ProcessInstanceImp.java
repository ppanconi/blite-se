/*
 *  DO NOT EDIT
 * 
 *  The contents of this file are subject to the terms
 *  of the GNU General Public License v3
 *  You may not use this file except
 *  in compliance with the License.
 * 
 *  You can obtain a copy of the license at
 *  http://www.gnu.org/licenses/gpl.html
 *  See the License for the specific language governing
 *  permissions and limitations under the License.
 * 
 */

package it.unifi.dsi.blitese.engine.runtime.imp;

import it.unifi.dsi.blitese.engine.definition.ActivityComponentFactory;
import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.ExecutionContext;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.InstanceMonitor;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance; 

import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import it.unifi.dsi.blitese.engine.runtime.RuntimeVariable;
import it.unifi.dsi.blitese.engine.runtime.VariableScope;
import it.unifi.dsi.blitese.parser.AServiceElement;
import it.unifi.dsi.blitese.parser.BLTDEFReceiveActivity;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class ProcessInstanceImp extends ABaseContext implements ProcessInstance, ExecutionContext, VariableScope {
    
    static Logger LOGGER = Logger.getLogger(ProcessInstanceImp.class.getName()); 
    
    private Engine mEngine;
    private ProcessManager mProcessManager;
    private String instanceId; 
    private BliteDeploymentDefinition deploymentDefinition;
    
    private Set<String> correlationSet = new HashSet<String>();

    private InstanceMonitor monitor;

    private int numberOfCreateRec = 0;

    public ProcessInstanceImp(Engine mEngine, ProcessManager mProcessManager, 
                              String instanceId, 
                              //SimpleNode bliteDefinition, 
                              BliteDeploymentDefinition deploymentDefinition) {
        this.mEngine = mEngine;
        this.mProcessManager = mProcessManager;
        this.instanceId = instanceId;
//        this.bliteDefinition = bliteDefinition;
        this.deploymentDefinition = deploymentDefinition;
        this.correlationSet = deploymentDefinition.getServiceElement().getCorrelationSet();

        this.numberOfCreateRec = deploymentDefinition.getServiceElement().getMAllCreateReceive().size();
    }
    
    public ProcessManager getManager() {
        return mProcessManager;
    }

    public String getInstanceId() {
        return instanceId;
    }
    
    public void activete() {

        AServiceElement startElement = deploymentDefinition.getServiceElement();
        
        FlowExecutor executor = new FlowExecutorImp(this);
        setExecutor(executor);
        registerFlow(executor);
        setSate(ContextState.RUNNING);
        
        BltDefBaseNode startDefNode = (BltDefBaseNode) startElement.getUniqueChild();
                
        ActivityComponent startActivity = ActivityComponentFactory.getInstance()
                .makeRuntimeActivity(startDefNode, this, this, executor);
        
        executor.setCurrentActivity(startActivity);
        mEngine.queueFlowExecutor(executor);
        
    }

    public void flowCompleted() {
        
        if (getState() != ContextState.FAULTED) {
            setSate(ContextState.COMPLETED);
        }
        
        LOGGER.info("Process Instance_______________________________________: " + instanceId + " " + getState().name());
    }

    public boolean doActivity() {
        throw new RuntimeException("The ProcessInstance never doActivity");
    }


    public BliteDeploymentDefinition getDeploymentDefinition() {
        return this.deploymentDefinition;
    }

    @Override
    public ProcessInstance getInstance() {
        return this;
    }


    ////////////////////////////////////////////////////////////////////////////
    // VariableScope implemetation
    private Map<String, RuntimeVariable> runTimaVars = 
            new HashMap<String, RuntimeVariable>();
    
    public RuntimeVariable getRuntimeVariable(String variable) {
        return runTimaVars.get(variable);
    }

    public void setRuntimeVariable(RuntimeVariable runtimeVariable) {
        runTimaVars.put(runtimeVariable.getVariable(), runtimeVariable);
    }

    public Map getRuntimeVariables() {
        return runTimaVars;
    }

    public RuntimeVariable createRuntimeVariable(String variable) {
        return new RuntimeVariableImp(variable, this);
    }

    public RuntimeVariable createNaddRuntimeVariable(String variable, Object value) {
        RuntimeVariableImp v = (RuntimeVariableImp) createRuntimeVariable(variable);
        v.setValue(value);
        setRuntimeVariable(v);
        return v;
    }

    // End VariableScope implemetation
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // ContextExecution implemetation
    public ProcessInstance getProcessInstance() {
        return this;
    }

    @Override
    public boolean matchCorrelation(String variable, Object incomingValue) {
        
        Object currentValue = null;
        if (getRuntimeVariable(variable) != null) 
            currentValue = getRuntimeVariable(variable).getValute();
        
        
        if (correlationSet != null && correlationSet.contains(variable)) {
            
//            if (currentValue == null || !currentValue.equals(incomingValue))
//                return false;
           
            if (currentValue != null && !currentValue.equals(incomingValue))
                    return false;
            
        }
        
        return true;
    }

    public void setMonitor(InstanceMonitor monitor) {
        this.monitor = monitor;
    }

    public InstanceMonitor getMonitor() {
        return monitor;
    }

    @Override
    public void setSate(ContextState state) {
        super.setSate(state);
        if (monitor != null) monitor.stateChanged();
    }



    ////////////////////////////////////////////////////////////////////////////
    // Added for sincronize activation
    private int activetedCreateRec = 0;

    public void createRecActivated(BLTDEFReceiveActivity rec) {
        activetedCreateRec++;

        LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + activetedCreateRec + "/" + numberOfCreateRec);

        if (activetedCreateRec >= numberOfCreateRec) {
            synchronized (this) {
                LOGGER.info("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOTIFYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY!!");
                this.notify();
            }
        }
    }

}

