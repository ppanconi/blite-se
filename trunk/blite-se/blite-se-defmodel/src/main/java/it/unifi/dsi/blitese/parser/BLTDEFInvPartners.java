/* Generated By:JJTree: Do not edit this line. BLTDEFInvPartners.java */

package it.unifi.dsi.blitese.parser;

public class BLTDEFInvPartners extends SimpleNode {
  public BLTDEFInvPartners(int id) {
    super(id);
  }

  public BLTDEFInvPartners(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
    ////////////////////////////////////////////////////////////////////////////
    //added code start here
    private BLTDEFPartnerId other;
    private Token respPartner;

    public Token getRespPartner() {
        return respPartner;
    }

    public void setRespPartner(Token respPartner) {
        this.respPartner = respPartner;
    }

    public String getRespPartnerName() {
        if (respPartner == null) return null;
        return respPartner.image;
    }

    public BLTDEFPartnerId getOther() {
        return other;
    }

    public void setOther(BLTDEFPartnerId other) {
        this.other = other;
    }
    
}