/* Generated By:JJTree: Do not edit this line. BLTDEFOperationId.java */

package it.unifi.dsi.blitese.parser;

public class BLTDEFOperationId extends SimpleNode {
  public BLTDEFOperationId(int id) {
    super(id);
  }

  public BLTDEFOperationId(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
 //added code start here ////////////////////////////////////////////////////////
    private String name;
    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
    
    public String getName() {
        return token.image;
    }

    
    
}