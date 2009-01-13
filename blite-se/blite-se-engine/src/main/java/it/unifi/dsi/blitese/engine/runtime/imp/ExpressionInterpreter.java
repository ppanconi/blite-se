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

import it.unifi.dsi.blitese.engine.runtime.RuntimeVariable;
import it.unifi.dsi.blitese.engine.runtime.VariableScope;
import it.unifi.dsi.blitese.parser.BLTDEFExpression;

/**
 * Evalute expressions on a VariableScope.
 * 
 * //TODO working in progress here...
 * 
 * @author panks
 */
public class ExpressionInterpreter {
    
    static public Object evaluate(VariableScope scope, BLTDEFExpression expression) {
    
//        Object value = expression.getLiteralValue();
//
//        if (value == null) {
//            String varName = expression.getVarableName();
//            RuntimeVariable rv = scope.getRuntimeVariable(varName);
//            if (rv != null) {
//                value = rv.getValute();
//            }
//        }

        Object value = expression.evaluate(scope);
        
        return value;
    }

}
