/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.parser;

/**
 *
 * @author panks
 */
public interface Expression {

    public Object evaluate(RuntimeValueProvider valueProvider);
}
