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

import javax.xml.namespace.QName;

/**
 * This is interface represents the communication channel between 
 * the Engine and the Communication Environment.  
 * 
 * @author panks
 */
public interface EngineChannel {

    /**
     * This method initialize a communication exchange from 
     * the process invoking process the requested endpoint
     * 
     * @param operation
     * @param messageContainer
     * @param instance the Process Instance initiatin the echange.
     * 
     * @return Object messageExchangeId 
     *         the indetificator key for the protocol state communication.
     *         
     */
     public Object createExchange(ServiceIdentifier serviceId, String operation, ProcessInstance instance);
   
    /**
     * Send done status for the response from the server for the invoke from
     * this SE as a client.
     * 
     * @param  inComingEventKey
     */
    public  void sendIntoExchange(Object messageExchangeId, MessageContainer messageContainer);

    /**
     * 
     * @param messageExchangeId
     */
    public void closeExchange(Object messageExchangeId);
}
