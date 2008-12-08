/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blitese.engine.runtime.InstanceMonitor;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author panks
 */
public class InstanceNode extends AbstractNode implements InstanceMonitor {

    static final String ICON_PATH = "it/unifi/dsi/blide/run/imp/instdef.png";

    public InstanceNode(ProcessInstance instance) {
        super(Children.LEAF, Lookups.fixed(instance));
        setName(instance.getInstanceId());

        setIconBaseWithExtension(ICON_PATH);
    }

    public void stateChanged() {
    }


}
