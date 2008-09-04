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
     * 
     * @param operation
     * @param messageContainer
     * @param instance
     * @return Object messageExchangeId 
     *         the indetificator key for the protocol state communication.
     *         
     * (In this particular case the communication pattern is the only One-way
     *          so it's the key for the connection state object on which recive the
     *          ack status response)
     */
    public Object invoke(ServiceIdentifier serviceId, String operation, 
            MessageContainer messageContainer, ProcessInstance instance);
    
    
    /**
     * Send done status for the response from the server for the invoke from
     * this SE as a client.
     * 
     * @param  inComingEventKey
     */
    public  void sendResponseDoneStatus(Object messageExchangeId);

}
