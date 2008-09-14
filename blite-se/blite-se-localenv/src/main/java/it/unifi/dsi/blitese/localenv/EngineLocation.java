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

import java.net.URL;

/**
 *
 * @author panks
 */
public class EngineLocation {
    
    private URL locationName;

    public EngineLocation() {
    }
    
    public EngineLocation(URL locationName) {
        this.locationName = locationName;
    }
    
    public URL getLocationName() {
        return locationName;
    }

    public void setLocationName(URL locationName) {
        this.locationName = locationName;
    }
    
    
        
}
