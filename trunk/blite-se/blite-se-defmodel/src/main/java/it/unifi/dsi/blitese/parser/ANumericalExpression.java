/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.parser;

/**
 *
 * @author panks
 */
public abstract class ANumericalExpression extends SimpleNode implements Expression {

    public ANumericalExpression(BliteParser p, int i) {
        super(p, i);
    }

    public ANumericalExpression(int i) {
        super(i);
    }

    public Object evaluate(RuntimeValueProvider valueProvider) {
        Expression exp1 = (Expression) jjtGetChild(0);
        Expression exp2 = (Expression) jjtGetChild(1);

        Object o1 = exp1.evaluate(valueProvider);
        Object o2 = exp2.evaluate(valueProvider);

        Number n1 = deriveNumber(o1);
        Number n2 = deriveNumber(o2);

        return compute(n1, n2);
    }

    private Number deriveNumber(Object o) {

        if (o instanceof Number) {
            Number number = (Number) o;
            return number;
        }

        if (o instanceof String) {
            String string = (String) o;

            return  string.length();
        }

        if (o instanceof Boolean) {
            Boolean b = (Boolean) o;
            if (b) return 1;
        }

        return 0;
    }

    public abstract Number compute(Number n1, Number n2);
}
