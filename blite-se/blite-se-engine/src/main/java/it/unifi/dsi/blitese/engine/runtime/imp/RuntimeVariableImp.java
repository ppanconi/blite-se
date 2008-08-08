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

/**
 *
 * @author panks
 */
public class RuntimeVariableImp implements RuntimeVariable {
    
    private Object value;
    private String variable;
    private VariableScope scope;

    public RuntimeVariableImp() {
    }

    public RuntimeVariableImp(VariableScope scope) {
        this.scope = scope;
    }
    
    public RuntimeVariableImp(String variable, VariableScope scope) {
        this.variable = variable;
        this.scope = scope;
    }
    
    public RuntimeVariableImp(Object value, String variable, VariableScope scope) {
        this.value = value;
        this.variable = variable;
        this.scope = scope;
    }
    
    ////////////////////////////////////////////////////////////////////////////

    public Object getValute() {
        return value;
    }

    public String getVariable() {
        return variable;
    }

    public VariableScope getScope() {
        return scope;
    }

    public void setScope(VariableScope scope) {
        this.scope = scope;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    
}
