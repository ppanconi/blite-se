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
import it.unifi.dsi.blitese.parser.ABltValueHolder;

/**
 *
 * @author panks
 */
public class RuntimeValueFactory {
    
    public static Object makeRuntimeValue(ABltValueHolder bltValueHolder, 
            VariableScope variableScope) {
        
        if (bltValueHolder.isLeteral()) return bltValueHolder.provideLiteralValue();
        
        String vName = bltValueHolder.getVarableName();
        
        RuntimeVariable rv = variableScope.getRuntimeVariable(vName);
        
        if (rv != null) {
            return rv.getValute();
        } else {
            return null;
        }
        
    }
    
    public static RuntimeVariable makeRuntimeValue(String variableName, Object value) {
       return new RuntimeVariableImp(value, variableName);
    }
    

}
