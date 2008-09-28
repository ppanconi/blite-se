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

import it.unifi.dsi.blitese.engine.runtime.MessageContent;

/**
 *
 * @author panks
 */
public class MessageContentImp implements MessageContent {
    
    private Object[] parts;

    public MessageContentImp(Object ... values) {
        
        parts = new Object[values.length];
        
        for (int i = 0; i < values.length; i++) {
            parts[i] = values[i];
        }
        
    }

    public Object[] getParts() {
        return parts;
    }

}
