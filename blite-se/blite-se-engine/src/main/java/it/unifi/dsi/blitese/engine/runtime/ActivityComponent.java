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

import it.unifi.dsi.blitese.parser.BltDefBaseNode;

/**
 * The base unit of runtime execution of a Runtime Process Instance.
 * 
 * The method <tt>doActivity</tt> it the key of the execution model.
 *
 * @author panks
 */
public interface ActivityComponent {

    static interface ActivityParmas {}
    
//    boolean doActivity(ActivityParmas parmas); 
    public boolean doActivity();

    public ProcessInstance getInstance();

    public ActivityComponent getParentComponent();

    public BltDefBaseNode getBltDefNode();

    public ExecutionContext getContext();
}
