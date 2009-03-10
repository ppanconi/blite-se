package it.unifi.dsi.blitese.engine.runtime;
...
/**
 * This class represents an activity execution context.
 *
 * This could be the Process Instance itself but also other
 * structured activities like scope activity.
 * 
 * At runtime Execution Contexts are in a tree 
 * 
 * @author panks
 */
public interface ExecutionContext extends VariableScope {
    
    ProcessInstance getProcessInstance();
    
    boolean matchCorrelation(String variable, Object value);

    ExecutionContext getParentContext();
    
    void registerInnerContext(ExecutionContext child);
    
    void notifyFault(Fault fault);
    
    public boolean isInAFaultedBranch();
    
    void registerFlow(FlowExecutor flow);
    
    void resumeWaithingFlows();
    
    public ContextState getState();
    
    public void setSate(ContextState state);
    
    public void addCompletedScope(AScope scope);
}
