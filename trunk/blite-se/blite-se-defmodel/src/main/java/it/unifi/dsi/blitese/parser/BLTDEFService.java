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

package it.unifi.dsi.blitese.parser;

import java.util.HashSet;
import java.util.Set;

public class BLTDEFService extends SimpleNode {
    
    

    public BLTDEFService(int id) {
        super(id);
    }

    public BLTDEFService(BliteParser p, int id) {
        super(p, id);
    }

      //added code start here
//    private Set<BltDefProcess> processDefs = new HashSet<BltDefProcess>();
    
    private Set<BLTDEFService> childs = new HashSet<BLTDEFService>();
    private BLTDEFServiceDef serviceDefinition;
    private BLTDEFServiceInstance serviceInstance;
    
    public Set<BLTDEFService> getChilds() {
        return childs;
    }

    public void setChilds(Set<BLTDEFService> childs) {
        this.childs = childs;
    }
    
    public Set<BLTDEFService> addServices(Set<BLTDEFService> services) {
        this.childs.addAll(services);
        return childs;
    }
    
    public Set<BLTDEFService> getAllServices() {
        childs.add(this);
        return childs;
    }

    public BLTDEFServiceDef getServiceDefinition() {
        return serviceDefinition;
    }

    public void setServiceDefinition(BLTDEFServiceDef serviceDefinition) {
        this.serviceDefinition = serviceDefinition;
    }

    public BLTDEFServiceInstance getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(BLTDEFServiceInstance serviceInstance) {
        this.serviceInstance = serviceInstance;
    }
   
}
