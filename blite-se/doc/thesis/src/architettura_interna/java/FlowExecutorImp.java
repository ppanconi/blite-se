package it.unifi.dsi.blitese.engine.runtime.imp;

...
/**
 * @author panks
 */
public class FlowExecutorImp implements FlowExecutor {
    
	...
	
    /**
     * This's the core of blite engine execution model.
     * 
     * The currente activity is executed until it's not the 
     * flowOwner.
     */
    public void executeCurrentActivity() {
        
        while (!(currentActivity.equals(flowOwner))) {

            boolean isNewCurrentActivitySet = currentActivity.doActivity();

            if (!isNewCurrentActivitySet) {
                return; //the flow is suspended
            }
        }

        flowOwner.flowCompleted(); //the flow has finished

    }
	
	...
}
