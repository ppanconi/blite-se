/* Generated By:JJTree: Do not edit this line. BLTDEFRecParams.java */

package it.unifi.dsi.blitese.parser;

import java.util.ArrayList;
import java.util.List;

public class BLTDEFRecParams extends SimpleNode {
  public BLTDEFRecParams(int id) {
    super(id);
  }

  public BLTDEFRecParams(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
//added code start here ////////////////////////////////////////////////////////
  
    private List<BLTDEFBoundId> formalParams = new ArrayList<BLTDEFBoundId>();

    public List<BLTDEFBoundId> getFormalParams() {
        return formalParams;
    }

    public void setFormalParams(List<BLTDEFBoundId> formalParams) {
        this.formalParams = formalParams;
    }
    
    public void addFormalParam(BLTDEFBoundId fparam) {
        formalParams.add(fparam);
    }
    
    public int provideFormalParamN() {
        return formalParams.size();
    }
    
    public boolean checkConformity(BLTDEFRecParams others) {
        return provideFormalParamN() == others.provideFormalParamN();
    }
  
}
