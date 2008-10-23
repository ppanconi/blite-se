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

import it.unifi.dsi.blitese.engine.definition.ActivityComponentFactory;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.ExecutionContext;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.RuntimeVariable;
import it.unifi.dsi.blitese.engine.runtime.imp.RuntimeValueFactory;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;
import it.unifi.dsi.blitese.parser.Node;

/**
 *
 * @author panks
 */
public abstract class ActivityComponentBase implements ActivityComponent {

    private BltDefBaseNode bltDefNode;
    private ExecutionContext context;
    private ActivityComponent parentComponent;
    private FlowExecutor executor;
    protected  int currentChildIndex = 0;
    private BltDefBaseNode currentChild = null;
    private Node node = null;

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
    
    
    public void init() {
        node = (Node) bltDefNode;
    }
    
    protected ActivityComponent nextChildActivity(ExecutionContext _context,
            FlowExecutor _executor) {
        if (currentChildIndex >= node.jjtGetNumChildren()) {
            currentChild = null;
            return null;
        } else {
            currentChild = (BltDefBaseNode) node.jjtGetChild(currentChildIndex++);
            return ActivityComponentFactory.getInstance().makeRuntimeActivity(currentChild, _context, this, _executor);
        }
    }
    
    protected boolean hasNextChildActivity() {
       if (currentChildIndex >= node.jjtGetNumChildren()) return false;
       else return true;
    }
    
    protected void flowParent() {
        getExecutor().setCurrentActivity(getParentComponent());
    }
    
    protected void assing(String varName, Object value) {
        RuntimeVariable rv = RuntimeValueFactory.makeRuntimeValue(varName, value);
        getContext().setRuntimeVariable(rv);
    }
}
