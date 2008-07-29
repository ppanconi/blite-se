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
package it.unifi.dsi.blitese.engine.definition;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.ExecutionContext;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ActivityComponentBase;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.InvokeActivity;
import it.unifi.dsi.blitese.parser.BLTDEFInvokeActivity;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class ActivityComponentFactory {

    private static final ActivityComponentFactory SINGLETON = new ActivityComponentFactory();

    private ActivityComponentFactory() {
    }

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
        try {
            Class actClass = provideActivityClass(bltDefNode);
            ActivityComponentBase activity = (ActivityComponentBase) actClass.newInstance();
            
            activity.setBltDefNode(bltDefNode);
            activity.setContext(context);
            activity.setExecutor(executor);
            activity.setParentComponent(parentComponent);

            return activity;

//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(ActivityComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
//            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ActivityComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ActivityComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }

    }
    
    private Class provideActivityClass(BltDefBaseNode bltDefNode) {
        
        if (bltDefNode instanceof BLTDEFInvokeActivity ) {
            return InvokeActivity.class;
        } else {
            throw new RuntimeException("Not yet supported Activity " + bltDefNode);
        }
        
//        Class actClass = Class.forName(bltDefNode.provideRuntimeActivity());
    }
    
}
