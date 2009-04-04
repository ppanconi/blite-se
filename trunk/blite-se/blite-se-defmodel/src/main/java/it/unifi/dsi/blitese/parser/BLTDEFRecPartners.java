/* Generated By:JJTree: Do not edit this line. BLTDEFRecPartners.java */

package it.unifi.dsi.blitese.parser;

public class BLTDEFRecPartners extends SimpleNode {
  public BLTDEFRecPartners(int id) {
    super(id);
  }

  public BLTDEFRecPartners(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
//added code start here ////////////////////////////////////////////////////////
  
    private Token recPartToken;
    private BLTDEFPartnerId otherPatnerId;

    public Token getRecPartToken() {
        return recPartToken;
    }

    public void setRecPartToken(Token recPartToken) {
        this.recPartToken = recPartToken;
    }

    public String getServiceName() {
        return recPartToken.image;
    }

    public BLTDEFPartnerId getOtherPatnerId() {
        return otherPatnerId;
    }

    public void setOtherPatnerId(BLTDEFPartnerId otherPatnerId) {
        this.otherPatnerId = otherPatnerId;
    }


}
