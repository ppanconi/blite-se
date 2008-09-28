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

import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.MessageContent;
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;

/**
 * This class create the appropriate instances of MessageContainer
 * objects.
 * 
 * @author panks
 */
public class MessageContainerFactory {

    private static MessageContainer createMessageContainer(MessageContent content, 
                                                           Object messageExchangeId, 
                                                           MessageContainer.Type type) {
        return new MessageContainerImp(messageExchangeId, content, type);
    }
    
    
    static public  MessageContainer createMessageContainer(MessageContent content, 
                                                           MessageContainer.Type type) {
       return new MessageContainerImp(content, type);
    }
    
//    static public MessageContainer createMessageContainer(Object content, Object applicationTraceId) {
//       return new MessageContainerImp(contect, MessageContainer.Type.MESSAGE, applicationTraceId);
//        throw new RuntimeException("Not Yet Imp");
//    }
    
    static public MessageContainer createMessageContainerForInvalidDestinatioPort(ServiceIdentifier serviceId, 
            Object exchangeId) {
        
        String s = "Invalid Service Identification: " + serviceId;
        MessageContent content = MessageContentFactory.createStringMC(s);
        
        return createMessageContainer(content, exchangeId, MessageContainer.Type.STATUS_ERROR);
        
    }
    
    static public MessageContainer createMessageContainerForDoneStatus(Object mexchangeId) {
        
        String s = "Ok DONE_STATUS for me : " + mexchangeId;
        MessageContent content = MessageContentFactory.createStringMC(s);
        
        return createMessageContainer(content, mexchangeId, MessageContainer.Type.STATUS_DONE);
    }
    
}
