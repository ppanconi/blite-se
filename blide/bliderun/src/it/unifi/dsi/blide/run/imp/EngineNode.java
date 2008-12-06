/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.localenv.EngineLocation;
import it.unifi.dsi.blitese.localenv.LocalEnvironment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author panks
 */
public class EngineNode extends AbstractNode {

    static final String ICON_PATH = "it/unifi/dsi/blide/run/imp/server.png";

    public EngineNode(LocalEnvironment le, Engine engine, EngineLocation location) {
        super(new EngineChildrens(le, engine), Lookups.singleton(engine) );

        setIconBaseWithExtension(ICON_PATH);
        setName("" + location);
    }

    public Engine getEngine() {
        return getLookup().lookup(Engine.class);
    }



    private static class EngineChildrens extends Children.Keys<BliteDeploymentDefinition>
            implements ChangeListener {

        private LocalEnvironment le;
        private Engine engine;

        public EngineChildrens(LocalEnvironment le, Engine engine) {
            this.le = le;
            this.engine = engine;
            le.addChangeListener(this);
        }

        @Override
        protected void addNotify() {
            setKeys(engine.provideDefinitions());
        }

        @Override
        protected Node[] createNodes(BliteDeploymentDefinition key) {
            return new Node[] {new DefinitionNode(engine, key)};
        }

        public void stateChanged(ChangeEvent e) {
            setKeys(engine.provideDefinitions());
        }

    }
}
