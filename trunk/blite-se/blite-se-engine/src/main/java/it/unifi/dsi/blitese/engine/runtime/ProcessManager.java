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


public interface ProcessManager {

    /**
     * Execute the invoke operation on the provided Partner Link 
     * with the provided Operation and passing the provided Message Container. 
     * 
     * @param runtimePartnerLink the key object to locate the target partner link,
     *        
     * @param operation
     * @param messageContainer
     * @param instance 
     * @return
     */
    public Object invoke(Object runtimePartnerLink, String operation, 
            MessageContainer messageContainer, ProcessInstance instance);
}

