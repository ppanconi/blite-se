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
import it.unifi.dsi.blitese.parser.BLTDEFDeployment;
import it.unifi.dsi.blitese.parser.BLTDEFServiceInstance;
import java.util.Set;

/**
 *
 * @author panks
 */
public class BliteDeploymentDefinitionImp implements BliteDeploymentDefinition {
    
    private BLTDEFDeployment mDEFDeployment;
    private Object id;

    public BliteDeploymentDefinitionImp(BLTDEFDeployment dEFDeployment) {
        if (dEFDeployment == null)
            throw new IllegalArgumentException("BLTDEFDeployment NULL");
        
        this.mDEFDeployment = dEFDeployment;
    }

    public BliteDeploymentDefinitionImp(BLTDEFDeployment mDEFDeployment, Object id) {
        if (mDEFDeployment == null)
            throw new IllegalArgumentException("BLTDEFDeployment NULL");

        this.mDEFDeployment = mDEFDeployment;
        this.id = id;
    }

    public Object getBliteId() {
        return id;
    }

    public Set<BLTDEFServiceInstance> getDefProcessInstances() {
        return mDEFDeployment.provideAllInsatnces();
    }

}
