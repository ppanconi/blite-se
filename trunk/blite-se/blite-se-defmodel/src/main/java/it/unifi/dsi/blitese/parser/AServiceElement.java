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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author panks
 */
public abstract class AServiceElement extends SimpleNode {

    private List<BLTDEFReceiveActivity> ports = new ArrayList<BLTDEFReceiveActivity>();
    
    //map the portId to the the definition list
    private HashMap<String, List<BLTDEFReceiveActivity>> mPortIdToDef = new HashMap<String, List<BLTDEFReceiveActivity>>();
    
    private Set<String> correlationSet = new HashSet<String>();
    
    public AServiceElement(BliteParser p, int i) {
        super(p, i);
    }

    public AServiceElement(int i) {
        super(i);
    }

    public SimpleNode getUniqueChild() {
        return (SimpleNode) jjtGetChild(0);
    }
    
    public void addPort(BLTDEFReceiveActivity port) {
        ports.add(port);
        
        if (!checkOperationConfomity(port)) throw new IllegalArgumentException("Not Conform port " + port);
        provideListForPort(port.getPortId()).add(port);
    }
    
    public List<BLTDEFReceiveActivity> getPorts() {
        return ports;
    }
    
    public HashMap<String, List<BLTDEFReceiveActivity>> getPortMapping() {
        return mPortIdToDef;
    }
    
    public List<String> provideAllServiceName() {
        List<String> names = new ArrayList<String>(ports.size());
        
        for (BLTDEFReceiveActivity port : getPorts()) {
            names.add(port.getPartners().getServiceName());
        }
        
        return names;
    }
    
    public boolean checkOperationConfomity(BLTDEFReceiveActivity activity) {
        
        String portId = activity.getPortId();
        
        List<BLTDEFReceiveActivity> l = provideListForPort(portId);
        if (l.size() > 0) {
            BLTDEFReceiveActivity previous = l.get(0);
            BLTDEFRecParams prevParams = previous.getParams();
            BLTDEFRecParams curParam = activity.getParams();
            
            return prevParams.checkConformity(curParam);
        }
        return true;
    }
    
    
    public List<BLTDEFReceiveActivity> provideListForPort(String portId) {
        List<BLTDEFReceiveActivity> l = mPortIdToDef.get(portId);
        
        if (l == null) {
            l = new ArrayList<BLTDEFReceiveActivity>();
            mPortIdToDef.put(portId, l);
        }
        
        return l;
    }
    
    public boolean isCreateInstancePort(String portId) {
        return false;
    }

    public Set<String> getCorrelationSet() {
        return correlationSet;
    }

    public void setCorrelationSet(Set<String> correlationSet) {
        this.correlationSet = correlationSet;
    }
    
    
}
