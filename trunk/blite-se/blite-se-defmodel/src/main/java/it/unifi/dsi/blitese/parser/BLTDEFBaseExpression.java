/* Generated By:JJTree: Do not edit this line. BLTDEFBaseExpression.java */

package it.unifi.dsi.blitese.parser;

public class BLTDEFBaseExpression extends ABltValueHolder implements Expression {
  public BLTDEFBaseExpression(int id) {
    super(id);
  }

  public BLTDEFBaseExpression(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

//added code start here ////////////////////////////////////////////////////////
    private Expression childExp;

    public Expression getChildExp() {
        return childExp;
    }

    public void setChildExp(Expression childExp) {
        this.childExp = childExp;
    }

    public Object evaluate(RuntimeValueProvider valueProvider) {
        if (getLiteralValue() != null) return getLiteralValue();
        if (getVarableName() != null) return valueProvider.provideValue(getVarableName());
        return getChildExp().evaluate(valueProvider);
    }
}
