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

import it.unifi.dsi.blitese.engine.runtime.InComingEventKey;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;

/**
 *
 * @author panks
 */
public class RequestInComingEventKey implements InComingEventKey {

    private ServiceIdentifier sid;
    private String portId;
    private MessageContainer mc;

    public RequestInComingEventKey(ServiceIdentifier sid, String portId) {
        this.sid = sid;
        this.portId = portId;
    }

    public RequestInComingEventKey(ServiceIdentifier sid, String portId, MessageContainer mc) {
        this.sid = sid;
        this.portId = portId;
        this.mc = mc;
    }

    public MessageContainer getMc() {
        return mc;
    }

    public void setMc(MessageContainer mc) {
        this.mc = mc;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public ServiceIdentifier getSid() {
        return sid;
    }

    public void setSid(ServiceIdentifier sid) {
        this.sid = sid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        
        String s = (InComingEventKeyFactory.IN_REQUEST_PREFIX + getPortId().toString()).toString();
        return s.hashCode();
    }
    
    
    
}
