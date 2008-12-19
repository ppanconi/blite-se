/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor;

import it.unifi.dsi.blide.run.api.InstanceVisualizer;
import it.unifi.dsi.blitese.engine.runtime.InstanceMonitor;
import it.unifi.dsi.blitese.localenv.LocalInstanceMonitor;

/**
 *
 * @author panks
 */
public class InstanceVisualizerImp implements InstanceVisualizer {

    public void visualizeInstance(InstanceMonitor monitor) {
        InstanceTopComponent win = new InstanceTopComponent((LocalInstanceMonitor)monitor);

        win.open();
        win.requestActive();
    }

}
