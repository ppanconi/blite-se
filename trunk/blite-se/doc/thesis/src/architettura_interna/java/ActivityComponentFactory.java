/**
 * Factory class to create different ActivityComponent implemetation Objects.    
 * @author panks
 */
public class ActivityComponentFactory {

    private static final ActivityComponentFactory SINGLETON = 
    											new ActivityComponentFactory();

    private ActivityComponentFactory() {}

    /**
     * gets singleton instance
     * @return ActivityComponentFactory 
     */
    public static ActivityComponentFactory getInstance() {
        return SINGLETON;
    }

    public ActivityComponent makeRuntimeActivity(BltDefBaseNode bltDefNode,
            									 ExecutionContext context,
            									 ActivityComponent parentComponent,
            									 FlowExecutor executor) {
    	...
    }
}