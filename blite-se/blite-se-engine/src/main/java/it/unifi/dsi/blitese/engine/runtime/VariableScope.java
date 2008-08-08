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

import java.util.Map;

/**
 * The VariableScope scopes the runtime variables at the appropriate levels.
 * The more generic Context interface extends this interface. This interface
 * is to be (via Context) implemented by  BPELProcessInstanceImpl (ProcessUnit),
 * ScopeUnit, CatchUnit and InvokeUnit. This interface can be used at other
 * places if local scoping of variables is required.
 * 
 * @author panks
 */
public interface VariableScope {

    /**
     * Gets the corresponding RuntimeVariable. If no matching RuntimeVariable is
     * available then a null is returned. This method takes care of overidden 
     * variables, that is, if a variable is defined in the current scope, then
     * the value of that variable is returned, if not, then the scope hierarchy
     * is searched upwards until a matching variable is found.
     * @param variable The variable for which RuntimeVariable is needed
     * @return The corresponding RuntimeVariable. Null if no match is found.
     */
    public RuntimeVariable getRuntimeVariable(String variable); 
   
   /**
     * Sets the value of the variable. Delegates the setting to a parent
     * VariableScope if the current VariableScope does not own the variable.
     * @param runtimeVariable The corresponding RuntimeVariable to be set
     * @throws UnsupportedOperationException If a VariableScope does not support this
     * functionality
     */
    public void setRuntimeVariable(RuntimeVariable runtimeVariable);
   
   /**
     * Gives a map of all the runtime variables visible from this VariableScope. 
     * The key is RVariable and the value is the RuntimeVariable associated with it. 
     * Whether the VariableScope has a variable visible can be found by the containsKey() 
     * method on the Map. The RuntimeVaribale value can be null (which means that the 
     * RuntimeVariable is not initialzed yet). Adding new variables to the map is not 
     * supported through the retuned Map.
     * @return Map of RuntimeVariables
     * @throws UnsupportedOperationException If a VariableScope does not support this
     * functionality
     */
    public Map getRuntimeVariables();
    
    /**
     * Helper method to create a variable. Checks to see if the variable belongs to this
     * scope context. If the variable is not defined in this scope context delegates it 
     * to the parent context. Hence it traverses up to the parent process context for 
     * variables defined in the process instance but used in embeded or nested scopes.
     * @param variable
     * @return
     */
    RuntimeVariable createRuntimeVariable(String variable);
    
    RuntimeVariable createNaddRuntimeVariable(String variable, Object value);
}
