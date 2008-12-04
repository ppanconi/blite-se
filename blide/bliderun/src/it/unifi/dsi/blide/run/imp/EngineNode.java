/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.localenv.EngineLocation;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author panks
 */
public class EngineNode extends AbstractNode {

    public EngineNode(Engine engine, EngineLocation location) {
        super(Children.LEAF);

        setName("" + location);
    }


}
