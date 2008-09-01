/* Generated By:JJTree: Do not edit this line. BLTDEFDeployments.java */

package it.unifi.dsi.blitese.parser;

import java.util.LinkedList;
import java.util.List;

public class BLTDEFDeployments extends SimpleNode {
  public BLTDEFDeployments(int id) {
    super(id);
  }

  public BLTDEFDeployments(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
//added code start here ////////////////////////////////////////////////////////
    private List<BLTDEFDeployment> deployments = new LinkedList<BLTDEFDeployment>();

    public List<BLTDEFDeployment> getDeployments() {
        return deployments;
    }
    
    public void addDeployment(BLTDEFDeployment deployment) {
        deployments.add(deployment);
    }
}
