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
package it.unifi.dsi.blitese.engine.definition;

import it.unifi.dsi.blitese.parser.AServiceElement;
import it.unifi.dsi.blitese.parser.BLTDEFServiceDef;
import it.unifi.dsi.blitese.parser.BLTDEFServiceInstance;
import java.util.Comparator;

/**
 * This the interface for the static definition of a Blite process (program)
 * (an object of abstract typeAServiceElement). 
 * 
 * The definition could be a real process definition (BLTDEFServiceDef) but also
 * a instance definition (BLTDEFServiceInstance) ready to run,
 * 
 * One of this object could be deployated on an Engine instance.
 *
 * @author panks
 */
public interface BliteDeploymentDefinition {

    /**
     * An unique (probably absolute) id for the Blite Process Definition
     * //TODO probably to change to an xml QName
     * @return Object (in future a QNAme)
     */
    Object getBliteId();
    
//    /**
//     * @return Set<BltDefServiceInstance> the set of statically defined 
//     * blite processes instaces.
//     */
//    Set<BLTDEFServiceInstance> getDefProcessInstances();
    
    AServiceElement getServiceElement();
    
    /**
     * The BLTDEFServiceDef if the AServiceElement is of that type,
     * null otherwise.
     * 
     * @return BLTDEFServiceDef
     */
    BLTDEFServiceDef provideServiceDefinition();
    
    /**
     * The BLTDEFServiceInstance if the AServiceElement is of that type,
     * null otherwise.
     * 
     * @return BLTDEFServiceInstance
     */
    BLTDEFServiceInstance provideServiceInstance();
    
    public class DefComparator implements Comparator<BliteDeploymentDefinition> {

        public int compare(BliteDeploymentDefinition o1, BliteDeploymentDefinition o2) {
            return o1.getBliteId().toString().compareTo(o2.getBliteId().toString());
        }
        
    }
    
}

