/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.parser;

/**
 *
 * @author panks
 */
public abstract class ALogicExpression extends SimpleNode implements Expression {

    public ALogicExpression(BliteParser p, int i) {
        super(p, i);
    }

    public ALogicExpression(int i) {
        super(i);
    }

    public Object evaluate(RuntimeValueProvider valueProvider) {
        Expression exp1 = (Expression) jjtGetChild(0);
        Object o1 = exp1.evaluate(valueProvider);
        Boolean b1 = ImplicitTypeConversion.deriveBoolean(o1);

        Boolean r1 = test(b1);
        if (r1 != null) return r1;

        Expression exp2 = (Expression) jjtGetChild(1);
        Object o2 = exp2.evaluate(valueProvider);
        Boolean b2 = ImplicitTypeConversion.deriveBoolean(o2);

        return test(b1, b2);
    }

    public abstract Boolean test(Boolean b1);

    public abstract Boolean test(Boolean b1, Boolean b2);
}
