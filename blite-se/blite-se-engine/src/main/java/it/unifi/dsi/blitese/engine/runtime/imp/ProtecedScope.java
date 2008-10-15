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
import it.unifi.dsi.blitese.engine.runtime.ExecutionContext;
import it.unifi.dsi.blitese.engine.runtime.Fault;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;

/**
 *
 * @author panks
 */
public class ProtecedScope extends ABaseContext {

    public ProtecedScope(BltDefBaseNode bltDefNode, ExecutionContext parentContext,
                         ActivityComponent parentComponent, FlowExecutor executor) {
        
        setContext(parentContext);
        setBltDefNode(bltDefNode);
        setParentComponent(parentComponent);
        setExecutor(executor);
        
    }

    /**
     * Override the nomrmal Scope behavior.
     * Because we had to protect the inner activities
     * against temination. 
     * 
     * @return true only if it has flauted otherwise flase
     * ingnoring the parent state.
     */
    @Override
    public boolean isInAFaultedBranch() {
        if (getState() == ContextState.FAULTED) {
            return true;
        } else 
            return false;
    }
    
    /**
     * Overriding the normal Scope behavior.
     * In conformity of Blite semantics we let
     * the falut raise up and so we notify the parent Context
     * 
     * The notification to wake our waiting flow
     * is let to the parent Context, it'll invoke <tt>resumeWaithingFlows</tt>
     * also on this Context as a normal innerContext.
     * 
     * @param fault
     */
    @Override
    public void notifyFault(Fault fault) {
        setSate(ContextState.FAULTED);
        getParentContext().notifyFault(fault);
        
    }

    public boolean doActivity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
