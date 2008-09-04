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

package it.unifi.dsi.blitese.localenv;

import it.unifi.dsi.blitese.engine.runtime.EngineChannel;
import it.unifi.dsi.blitese.engine.runtime.InComingEventKey;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;

/**
 *
 * @author panks
 */
public class LocalEngineChannel implements EngineChannel {
    
    private long messageExchangeCounter = 0L;
    

    public Object invoke(ServiceIdentifier serviceId, String operation, MessageContainer messageContainer, ProcessInstance instance) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendResponseDoneStatus(Object messageExchangeId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

}
