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
package it.unifi.dsi.blitese.engine.runtime.activities.imp;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.ExecutionContext;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;

/**
 *
 * @author panks
 */
public abstract class ActivityComponentBase implements ActivityComponent {

    private BltDefBaseNode bltDefNode;
    private ExecutionContext context;
    private ActivityComponent parentComponent;
    private FlowExecutor executor;

    public BltDefBaseNode getBltDefNode() {
        return bltDefNode;
    }

    public void setBltDefNode(BltDefBaseNode bltDefNode) {
        this.bltDefNode = bltDefNode;
    }

    public ExecutionContext getContext() {
        return context;
    }

    public void setContext(ExecutionContext context) {
        this.context = context;
    }

    public FlowExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(FlowExecutor executor) {
        this.executor = executor;
    }

    public ActivityComponent getParentComponent() {
        return parentComponent;
    }

    public void setParentComponent(ActivityComponent parentComponent) {
        this.parentComponent = parentComponent;
    }
    
}
