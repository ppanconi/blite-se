/* Generated By:JJTree: Do not edit this line. BLTDEFBooleanLiteral.java */

package it.unifi.dsi.blitese.parser;

public class BLTDEFBooleanLiteral extends ALiteral {
  public BLTDEFBooleanLiteral(int id) {
    super(id);
  }

  public BLTDEFBooleanLiteral(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

//added code start here ////////////////////////////////////////////////////////
    @Override
    public Object parseValue() {
        String boolStr = getToken().image;

        return Boolean.parseBoolean(boolStr);
    }
}