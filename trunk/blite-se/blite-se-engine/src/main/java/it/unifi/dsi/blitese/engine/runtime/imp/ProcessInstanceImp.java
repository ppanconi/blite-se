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
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance; 

import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import it.unifi.dsi.blitese.engine.runtime.RuntimeVariable;
import it.unifi.dsi.blitese.engine.runtime.VariableScope;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;
import it.unifi.dsi.blitese.parser.SimpleNode;
import java.util.HashMap;
import java.util.Map;

public class ProcessInstanceImp implements ProcessInstance, ExecutionContext, VariableScope {
    
    private Engine mEngine;
    private ProcessManager mProcessManager;
    private String instanceId; 
    private BliteDeploymentDefinition deploymentDefinition;
    private BltDefBaseNode bliteDefinition;

    public ProcessInstanceImp(Engine mEngine, ProcessManager mProcessManager, 
                              String instanceId, 
                              //SimpleNode bliteDefinition, 
                              BliteDeploymentDefinition deploymentDefinition) {
        this.mEngine = mEngine;
        this.mProcessManager = mProcessManager;
        this.instanceId = instanceId;
//        this.bliteDefinition = bliteDefinition;
        this.deploymentDefinition = deploymentDefinition;
    }
    
    public void activete(BltDefBaseNode defStartNode) {

        this.bliteDefinition = defStartNode;
        FlowExecutor executor = new FlowExecutorImp(this);
        
        BltDefBaseNode myChilDDefNode = (BltDefBaseNode) 
                ((SimpleNode)bliteDefinition).jjtGetChild(0);
                
        ActivityComponent myChilsActivity = ActivityComponentFactory.getInstance()
                .makeRuntimeActivity(myChilDDefNode, this, this, executor);
        
        executor.setCurrentActivity(myChilsActivity);
        mEngine.queueFlowExecutor(executor);
        
    }

    public void flowCompleted() {
        
        System.out.println("Process Instance " + instanceId + " completed");
        
    }

    public boolean doActivity() {
        return false;
    }


    public BliteDeploymentDefinition getDeploymentDefinition() {
        return this.deploymentDefinition;
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

}

