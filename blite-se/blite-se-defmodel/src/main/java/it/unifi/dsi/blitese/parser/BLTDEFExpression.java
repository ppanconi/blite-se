/* Generated By:JJTree: Do not edit this line. BLTDEFExpression.java */

package it.unifi.dsi.blitese.parser;

//TODO temporary solution extendid simple ValueHolder
public class BLTDEFExpression 
        //extends ABltValueHolder {
        extends SimpleNode
        implements Expression {
  public BLTDEFExpression(int id) {
    super(id);
  }

  public BLTDEFExpression(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

//added code start here ////////////////////////////////////////////////////////
    private Expression childExp = null;

    public Expression getChildExp() {
        return childExp;
    }

    public void setChildExp(Expression childExp) {
        this.childExp = childExp;
    }


    public Object evaluate(RuntimeValueProvider valueProvider) {
        return getChildExp().evaluate(valueProvider);
    }
}
