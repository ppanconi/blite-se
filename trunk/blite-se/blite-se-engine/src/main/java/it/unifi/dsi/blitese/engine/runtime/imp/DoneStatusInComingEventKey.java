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

/**
 *
 * @author panks
 */
public class DoneStatusInComingEventKey implements InComingEventKey {
    
    private Object meId;

    DoneStatusInComingEventKey(Object meId) {
        
        if (meId == null) throw new IllegalArgumentException("");
        this.meId = meId;
    }

    @Override
    public int hashCode() {
        return (InComingEventKeyFactory.DONE_STATUS_PREFIX + meId.toString()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) return false;
        
        return hashCode() == obj.hashCode();
    }

    public Object getMeId() {
        return meId;
    }

    public void setMeId(Object meId) {
        this.meId = meId;
    }
    
    
    
    

}
