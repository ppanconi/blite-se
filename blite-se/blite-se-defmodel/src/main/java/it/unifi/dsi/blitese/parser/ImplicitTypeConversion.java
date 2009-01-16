/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.parser;

/**
 * Policies to derive implicit type conversion
 * betwen blite object values.
 *
 * @author panks
 */
public class ImplicitTypeConversion {

    static public Number deriveNumber(Object o) {

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

    static public Boolean deriveBoolean(Object o) {

        if (o instanceof Number) {
            Number number = (Number) o;
            if (number.doubleValue() == 1.0) {
                return Boolean.TRUE;
            }
        }

        if (o instanceof String) {
            String string = (String) o;

            if ("true".equalsIgnoreCase(string)) {
                return Boolean.TRUE;
            }
        }

        if (o instanceof Boolean) {
            Boolean b = (Boolean) o;
            return b;
        }

        return Boolean.FALSE;
    }

    static public String deriveString(Object o) {
        return "" + o;
    }
}
