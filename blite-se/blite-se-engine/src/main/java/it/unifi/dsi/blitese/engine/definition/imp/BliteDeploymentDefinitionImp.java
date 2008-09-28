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

package it.unifi.dsi.blitese.engine.definition.imp;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.parser.AServiceElement;
import it.unifi.dsi.blitese.parser.BLTDEFDeployment;
import it.unifi.dsi.blitese.parser.BLTDEFServiceDef;
import it.unifi.dsi.blitese.parser.BLTDEFServiceInstance;
import java.util.Set;

/**
 *
 * @author panks
 */
public class BliteDeploymentDefinitionImp implements BliteDeploymentDefinition {

    private AServiceElement serviceElement;
    private Object id;

    public BliteDeploymentDefinitionImp(AServiceElement serviceElement) {
        if (serviceElement == null)
            throw new IllegalArgumentException("AServiceElement NULL");
            
        this.serviceElement = serviceElement;
    }

    public BliteDeploymentDefinitionImp(AServiceElement serviceElement, Object id) {
        if (serviceElement == null)
            throw new IllegalArgumentException("AServiceElement NULL");

        this.serviceElement = serviceElement;
        this.id = id;
    }

    public Object getBliteId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }
    

    public AServiceElement getServiceElement() {
        return serviceElement;
    }

    public BLTDEFServiceDef provideServiceDefinition() {
        if (serviceElement instanceof BLTDEFServiceDef) {
            BLTDEFServiceDef bLTDEFServiceDef = (BLTDEFServiceDef) serviceElement;
            return bLTDEFServiceDef;
        } else
            return null;
    }

    public BLTDEFServiceInstance provideServiceInstance() {
        if (serviceElement instanceof BLTDEFServiceInstance) {
            BLTDEFServiceInstance bLTDEFServiceInstance = (BLTDEFServiceInstance) serviceElement;
            return bLTDEFServiceInstance;
        } else
            return null;
    }

//    public Set<BLTDEFServiceInstance> getDefProcessInstances() {
//        return mDEFDeployment.provideAllInsatnces();
//    }

}
