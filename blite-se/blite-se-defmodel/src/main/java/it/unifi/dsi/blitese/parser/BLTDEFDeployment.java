/* Generated By:JJTree: Do not edit this line. BLTDEFDeployment.java */

package it.unifi.dsi.blitese.parser;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BLTDEFDeployment extends SimpleNode {
    
    
    
  public BLTDEFDeployment(int id) {
    super(id);
  }

  public BLTDEFDeployment(BliteParser p, int id) {
    super(p, id);
  }

//added code start here
    private Set<BLTDEFService> services = new HashSet<BLTDEFService>();

    public Set<BLTDEFService> getServices() {
        return services;
    }

    public void setServices(Set<BLTDEFService> services) {
        this.services = services;
    }

    /**
     * @return BLTDEFServiceDef the service definition in the deployment
     * if it exists.
     */
    public BLTDEFServiceDef provideServiceDefinition() {
        for (BLTDEFService service : services) {
            BLTDEFServiceDef def = service.getServiceDefinition();
            if (def != null) return def;
        }
        return null;
    }
    
    public Set<BLTDEFServiceInstance> provideAllInsatnces() {
        Set<BLTDEFServiceInstance> ints = new HashSet<BLTDEFServiceInstance>();
        
        for (BLTDEFService s : services) {
            BLTDEFServiceInstance i = s.getServiceInstance();
            if (i != null) ints.add(i);
        }
        
        return ints;
    }
    
    /**
     * @return The Set of abstract AServiceElements defined in this deploy.
     * They are the BLTDEFServiceDef if present and all BLTDEFServiceInstance
     * mixed in the same collectiom returned.
     */
    public Set<AServiceElement> provideAllServiceElement() {
        
        Set<AServiceElement> elements = new HashSet<AServiceElement>();
        
        BLTDEFServiceDef def = provideServiceDefinition();

        if (def != null) elements.add(def);
        
        elements.addAll(provideAllInsatnces());
        
        return elements;
    }
    
    public URL getLocation() {
        return null;
    }

    public Object provideDeployId(String locationName, AServiceElement serviceElement, int index) {
        return locationName + "/" + index;
    }
 
}
