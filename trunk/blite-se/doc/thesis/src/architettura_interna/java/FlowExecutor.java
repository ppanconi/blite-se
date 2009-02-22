package it.unifi.dsi.blitese.engine.runtime;

/**
 * These objects are one to one related to actived running flow on the engine.
 * It's possible to set to current Activity and to execute it with the mathod
 * <tt>executeCurrentActivity()</tt>.
 *
 * @author panks
 */
public interface FlowExecutor {
    
    void setCurrentActivity(ActivityComponent activityComponent);
    
    ActivityComponent getCurrentActivity();
    
    FlowOwner getOwner();

    void executeCurrentActivity();
}
