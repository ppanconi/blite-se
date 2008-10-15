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

import it.unifi.dsi.blitese.engine.runtime.imp.ExpressionInterpreter;
import it.unifi.dsi.blitese.parser.BLTDEFAssignActivity;
import it.unifi.dsi.blitese.parser.BLTDEFBoundId;
import it.unifi.dsi.blitese.parser.BLTDEFExpression;

/**
 *
 * @author panks
 */
//TODO
public class AssignActivity extends ActivityComponentBase {

    private BLTDEFAssignActivity assignDef;
    
    @Override
    public void init() {
        super.init();
        assignDef = (BLTDEFAssignActivity) getBltDefNode();
    }
    
    

    public boolean doActivity() {
        
        //we check if we had the input to terminate
        if (!getContext().isInAFaultedBranch()) {
            BLTDEFBoundId var = (BLTDEFBoundId) assignDef.jjtGetChild(0);
            BLTDEFExpression expr = (BLTDEFExpression) assignDef.jjtGetChild(1);
        
            Object exprValue = ExpressionInterpreter.evaluate(getContext(), expr);
        
            assing(var.getName(), exprValue);
        }
        
        flowParent();
        return true;
    }

}
