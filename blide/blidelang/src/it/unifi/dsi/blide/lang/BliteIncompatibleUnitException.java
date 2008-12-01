/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.lang;

/**
 *
 * @author panks
 */
public class BliteIncompatibleUnitException extends Exception {

    /**
     * Creates a new instance of <code>BliteIncompatibleUnitException</code> without detail message.
     */
    public BliteIncompatibleUnitException() {
    }


    /**
     * Constructs an instance of <code>BliteIncompatibleUnitException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BliteIncompatibleUnitException(String msg) {
        super(msg);
    }
}
