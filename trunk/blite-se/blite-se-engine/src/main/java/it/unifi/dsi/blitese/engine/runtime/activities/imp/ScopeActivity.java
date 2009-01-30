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
import it.unifi.dsi.blitese.engine.runtime.imp.ABaseContext;
import it.unifi.dsi.blitese.engine.runtime.imp.ProtecedScope;
import it.unifi.dsi.blitese.parser.AScope;
import it.unifi.dsi.blitese.parser.BLTDEFSequenceActivity;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;
import it.unifi.dsi.blitese.parser.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class ScopeActivity extends ABaseContext {

    static Logger LOGGER = Logger.getLogger(ScopeActivity.class.getName()); 
    
    private AScope scopeDef;
    @Override
    public void init() {
        super.init();
        scopeDef = (AScope) getBltDefNode();
    }
    
    public boolean doActivity() {
        if (isInAFaultedBranch()) {
            
            if (getState() == ContextState.FAULTED) {
                
                //DONE WE HAVE TO START Compesation/Faluts Handlers
                BltDefBaseNode falutHadlerDef = scopeDef.getFaultHandler();
                
                LOGGER.info("Scope " + this + " FAULTED starting Compensation FaultHandler");

                
                List<Node> compesation = provideConpesationDef();
                compesation.add((Node)falutHadlerDef);
                BLTDEFSequenceActivity seq = new BLTDEFSequenceActivity(compesation.toArray(new Node[compesation.size()]));

                ProtecedScope ps = 
                        new ProtecedScope(seq, getParentContext(), getParentComponent(), getExecutor(), this);
                
                getExecutor().setCurrentActivity(ps);
                return true;
                
            } else {
                setSate(ContextState.TEMINATED);
                
                LOGGER.info("Scope " + this + " TERMINATED");
                //WHAT WE HAVE TO DO HERE??? 
                //THE Blite doesn't say much...
                flowParent();
                return true;
            }
            
        } else {
           
            if (getState() == ContextState.STARTED) {
                setSate(ContextState.RUNNING);
                
                LOGGER.info("Scope " + this + " RUNNING");
                
                BltDefBaseNode mainActDef = scopeDef.getMainActivity();
                 
                ActivityComponent mainAct = ActivityComponentFactory.getInstance()
                        .makeRuntimeActivity(mainActDef, this, this, getExecutor());
                
                getExecutor().setCurrentActivity(mainAct);
                return true;
                
            } else if (getState() ==  ContextState.RUNNING) {
                setSate(ContextState.COMPLETED);
                
                //DONE put this scope as COMPLETED in the context
                getParentContext().addCompletedScope(scopeDef);

                LOGGER.info("Scope " + this + " COMPLETED");
                
                flowParent();
                return true;
                
            } else
                throw new IllegalStateException("Actual is not legal call doActivity on a " + getState().name() + " Scope");
            
        }
    }

}
