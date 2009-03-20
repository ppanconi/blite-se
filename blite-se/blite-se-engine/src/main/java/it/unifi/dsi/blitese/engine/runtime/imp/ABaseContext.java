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

import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.ExecutionContext;
import it.unifi.dsi.blitese.engine.runtime.Fault;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import it.unifi.dsi.blitese.engine.runtime.RuntimeVariable;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ActivityComponentBase;
import it.unifi.dsi.blitese.parser.AScope;
import it.unifi.dsi.blitese.parser.Node;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Abstract utility class that provides the base functionalities 
 * of a generic ExecutionContext 
 * 
 * @author panks
 */
public abstract class ABaseContext extends ActivityComponentBase implements ExecutionContext {
    
    static public enum ContextState {
        STARTED,
        RUNNING,
        COMPLETED,
        TEMINATED,
        FAULTED
    }
    
    private ContextState currentSate = ContextState.STARTED;

    public ContextState getState() {
        return currentSate;
    }

    public void setSate(ContextState state) {
        this.currentSate = state;
    }
    
    private ExecutionContext parent;
    private Set<ExecutionContext> innerContexts = new HashSet<ExecutionContext>();
    private List<FlowExecutor> registeredFlow = new ArrayList<FlowExecutor>();

    public void setParentContext(ExecutionContext parent) {
        this.parent = parent;
        parent.registerInnerContext(this);
    }

    /**
     * The Execution Context of this object as Activity is the parent Context
     * of these objects as Contexts.
     * 
     * @param context
     */
    @Override
    public void setContext(ExecutionContext context) {
        super.setContext(context);
        setParentContext(context);
    }

    public ExecutionContext getParentContext() {
        return parent;
    }

    public void registerFlow(FlowExecutor flow) {
        registeredFlow.add(flow);
    }

    public void registerInnerContext(ExecutionContext child) {
        innerContexts.add(child);
    }

    public void resumeWaithingFlows() {
        
        for (ExecutionContext innerContext : innerContexts) {
            innerContext.resumeWaithingFlows();
        }
        
        for (FlowExecutor flow : registeredFlow) {
            
            ProcessManager manager = getProcessInstance().getManager();
            Engine engine = manager.getEngine();
            
            //!!IMPORTANT!! To terminate the the next wating
            // activities we need to close this operation in a Process Level Mutual Exclusion
            //block.
            synchronized (manager.getDefinitionProcessLevelLock()) {
                engine.resumeWaitingFlow(flow);
            }
            
        }
    }

    public void notifyFault(Fault fault) {
        setSate(ContextState.FAULTED);
        resumeWaithingFlows();
    }

    public boolean isInAFaultedBranch() {
        
        if (getState() == ContextState.FAULTED) 
            return true;
                
        if (getParentContext()!= null)
            return getParentContext().isInAFaultedBranch();
        else return false;
    }

    public ProcessInstance getProcessInstance() {
        return getParentContext().getProcessInstance();
    }

    public boolean matchCorrelation(String variable, Object value) {
        return getParentContext().matchCorrelation(variable, value);
    }

    public RuntimeVariable createNaddRuntimeVariable(String variable, Object value) {
        return getParentContext().createNaddRuntimeVariable(variable, value);
    }

    public RuntimeVariable createRuntimeVariable(String variable) {
        return getParentContext().createRuntimeVariable(variable);
    }

    public RuntimeVariable getRuntimeVariable(String variable) {
        return getParentContext().getRuntimeVariable(variable);
    }

    public Map getRuntimeVariables() {
        return getParentContext().getRuntimeVariables();
    }

    public void setRuntimeVariable(RuntimeVariable runtimeVariable) {
        getParentContext().setRuntimeVariable(runtimeVariable);
    }

    public Object provideValue(String variableName) {
        RuntimeVariable rv = getRuntimeVariable(variableName);
        if (rv != null) {
            return rv.getValute();
        } else
            return null;

    }
    
    //--------------------------------------------------------------------------

    private List<AScope> completedSubScopes = new ArrayList<AScope>();


    synchronized protected List<Node> provideConpesationDef() {
        
        List<Node> l = new ArrayList<Node>();
        
        for (AScope scope : completedSubScopes) {
            Node n = (Node) scope.getCompensationHandler();
            if (n != null) l.add(n);
        }
        
        return l;
    }


    synchronized public void addCompletedScope(AScope scope) {
        completedSubScopes.add(scope);
    }

    synchronized public void addCompletedScopes(List<AScope> scopes) {
        completedSubScopes.addAll(scopes);
    }
}
