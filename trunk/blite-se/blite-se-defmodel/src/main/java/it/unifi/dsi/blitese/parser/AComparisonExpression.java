/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.parser;

/**
 *
 * @author panks
 */
public abstract class AComparisonExpression extends SimpleNode implements Expression {

    public AComparisonExpression(BliteParser p, int i) {
        super(p, i);
    }

    public AComparisonExpression(int i) {
        super(i);
    }

    public Object evaluate(RuntimeValueProvider valueProvider) {
        Expression exp1 = (Expression) jjtGetChild(0);
        Expression exp2 = (Expression) jjtGetChild(1);

        Object o1 = exp1.evaluate(valueProvider);
        Object o2 = exp2.evaluate(valueProvider);

        Number n1 = ImplicitTypeConversion.deriveNumber(o1);
        Number n2 = ImplicitTypeConversion.deriveNumber(o2);

        return compare(n1, n2);
    }

    public abstract Boolean compare(Number n1, Number n2);

}
