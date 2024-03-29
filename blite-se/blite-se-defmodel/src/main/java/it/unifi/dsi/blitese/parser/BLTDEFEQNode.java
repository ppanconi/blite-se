/* Generated By:JJTree: Do not edit this line. BLTDEFEQNode.java */

package it.unifi.dsi.blitese.parser;

/**
 * Rapresent equality test
 * @author panks
 */
public class BLTDEFEQNode extends AComparisonExpression {

    public BLTDEFEQNode(int id) {
    super(id);
  }

  public BLTDEFEQNode(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

    @Override
    public Boolean compare(Number n1, Number n2) {
        return n1.equals(n2);
    }


}
