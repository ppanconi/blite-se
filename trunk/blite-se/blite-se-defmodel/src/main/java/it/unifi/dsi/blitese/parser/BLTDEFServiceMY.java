/* Generated By:JJTree: Do not edit this line. BLTDEFServiceMY.java */
package it.unifi.dsi.blitese.parser;

import java.util.HashSet;
import java.util.Set;

public class BLTDEFServiceMY extends SimpleNode {
    
    private Set<BltDefProcess> processDefs = new HashSet<BltDefProcess>();

    public BLTDEFServiceMY(int id) {
        super(id);
    }

    public BLTDEFServiceMY(BliteParser p, int id) {
        super(p, id);
    }
    
    void addDefProcess(BltDefProcess bltDefProcess) {
        processDefs.add(bltDefProcess);
    }

    public Set<BltDefProcess> getProcessDefs() {
        return processDefs;
    }
    
}
