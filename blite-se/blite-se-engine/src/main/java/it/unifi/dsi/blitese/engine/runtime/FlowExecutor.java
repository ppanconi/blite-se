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

package it.unifi.dsi.blitese.engine.runtime;

/**
 * These obejetc are one to one related to actived running flow on the engine.
 * It's possible to set to current Activity and to execute it with the mathod
 * <tt>executeCurrentActivity()</tt>.
 *
 * @author panks
 */
public interface FlowExecutor {
    
    void setCurrentActivity(ActivityComponent activityComponent);
    
    ActivityComponent getCurrentActivity();

    void executeCurrentActivity();
}
