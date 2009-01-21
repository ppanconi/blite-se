/* Generated By:JJTree: Do not edit this line. BLTDEFGTNode.java */

package it.unifi.dsi.blitese.parser;

/**
 * >
 * @author panks
 */
public class BLTDEFGTNode extends AComparisonExpression {
  public BLTDEFGTNode(int id) {
    super(id);
  }

  public BLTDEFGTNode(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

    @Override
    public Boolean compare(Number n1, Number n2) {
        return n1.doubleValue() > n2.doubleValue();
    }
}
