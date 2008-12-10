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

import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BLTDEFDeployment;

/**
 *
 * @author panks
 */
public class EngineLocation implements Comparable<EngineLocation> {
    
    private String locationName;
    
//    public EngineLocation() {
//    }
//    
    private EngineLocation(String locationName) {
        setLocationName(locationName);
    }
    
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
//        if (locationName == null) throw new IllegalArgumentException("locationName cant be null");
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        if (getLocationName() == null) return super.toString();
        return getLocationName();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) return true;
        
        if (obj instanceof EngineLocation) {
            EngineLocation engineLocation = (EngineLocation) obj;
            return toString().equals(engineLocation.toString());
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public int compareTo(EngineLocation o) {
        return toString().compareTo(o.toString());
    }

    
    public static EngineLocation make(BLTDEFCompilationUnit compilationUnit,
                                      BLTDEFDeployment deployment) {

        String locName = compilationUnit.provideLocationName(deployment);
        EngineLocation loc = new EngineLocation(locName);

        return loc;
    }
}
