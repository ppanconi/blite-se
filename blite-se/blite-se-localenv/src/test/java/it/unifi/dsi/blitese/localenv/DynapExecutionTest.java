/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.localenv;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 * @author panks
 */
public class DynapExecutionTest extends AExecution {

    public DynapExecutionTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DynapExecutionTest.class);
    }

    @Override
    String getFileName() {
        return "dynap.blt";
    }
}