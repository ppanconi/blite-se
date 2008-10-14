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

import it.unifi.dsi.blitese.engine.runtime.ExecutionContext;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract utility class that provides the base functionalities 
 * of a generic ExecutionContext 
 * 
 * @author panks
 */
public abstract class ABaseContext implements ExecutionContext {

    private ExecutionContext parent;
    private List<ExecutionContext> innerContexts = new ArrayList<ExecutionContext>();
    private List<FlowExecutor> registeredFlow = new ArrayList<FlowExecutor>();

    public void setParent(ExecutionContext parent) {
        this.parent = parent;
    }

    public ExecutionContext getParent() {
        return parent;
    }

    public void registerFlow(FlowExecutor flow) {
        registeredFlow.add(flow);
    }

    public void registerInnerContext(ExecutionContext child) {
        innerContexts.add(child);
    }

    public void resumeWaithingFlows() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
