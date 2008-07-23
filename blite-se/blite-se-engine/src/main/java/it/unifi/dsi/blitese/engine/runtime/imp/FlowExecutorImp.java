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

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.FlowOwner;

/**
 *
 * @author panks
 */
public class FlowExecutorImp implements FlowExecutor {
    
    private ActivityComponent currentActivity;
    private FlowOwner flowOwner;

    public FlowExecutorImp(FlowOwner flowOwner) {
        if (flowOwner == null)
            throw new IllegalArgumentException("It's impossible to create a FlowExecutor with a null flowOwner");
        this.flowOwner = flowOwner;
    }

    public void setCurrentActivity(ActivityComponent activityComponent) {
        if (activityComponent == null)
            throw new IllegalArgumentException("Null activityComponent is not allowed");
        this.currentActivity = activityComponent;
    }

    public ActivityComponent getCurrentActivity() {
        return currentActivity;
    }

    public void executeCurrentActivity() {
        
        while (!(currentActivity.equals(flowOwner))) {
            boolean isNewCurrentActivitySet = currentActivity.doActivity();

            if (!isNewCurrentActivitySet) {
                return;
            }
        }

        flowOwner.flowCompleted();

    }

}
