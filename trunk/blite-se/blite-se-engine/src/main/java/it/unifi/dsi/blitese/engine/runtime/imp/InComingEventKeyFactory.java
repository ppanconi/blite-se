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

/**
 * A Factory class to create the key for incoming event
 * 
 * @author panks
 */
public class InComingEventKeyFactory {
    
    static public DoneStatusInComingEventKey createDoneStatusInComingEventKey(Object meId) {
      
        return new DoneStatusInComingEventKey(meId);
        
        
    }

}
