/* Generated By:JJTree: Do not edit this line. BLTDEFCompilationUnit.java */

package it.unifi.dsi.blitese.parser;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class BLTDEFCompilationUnit extends SimpleNode {
  public BLTDEFCompilationUnit(int id) {
    super(id);
  }

  public BLTDEFCompilationUnit(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
//added code start here ////////////////////////////////////////////////////////
    private List<BLTDEFDeployment> deployments = new LinkedList<BLTDEFDeployment>();
    private URL resource;
    
    public List<BLTDEFDeployment> getDeployments() {
        return deployments;
    }
    
    public void addDeployments(List<BLTDEFDeployment> deployments) {
        this.deployments.addAll(deployments);
    }

    public URL getResource() {
        return resource;
    }

    public void setResource(URL resource) {
        this.resource = resource;
    }

    
    
}
