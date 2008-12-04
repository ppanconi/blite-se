/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

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
public class EnvNode extends AbstractNode {

    static final String ICON_PATH = "it/unifi/dsi/blide/run/imp/servers.png";

    public EnvNode(LocalEnvironment le) {
        
        super(new EnvChildrens(le), Lookups.singleton(le));
        
        setIconBaseWithExtension(ICON_PATH);
        setDisplayName("Local Engines");
    }

    public LocalEnvironment getEnviroment() {
        return getLookup().lookup(LocalEnvironment.class);
    }


    private static class EnvChildrens extends Children.Keys<EngineLocation>
            implements ChangeListener {
        
        private LocalEnvironment le;

        public EnvChildrens(LocalEnvironment le) {
            this.le = le;
            le.addChangeListener(this);
        }

        @Override
        protected void addNotify() {
            setKeys(le.provideSortLocations());
        }



        @Override
        protected Node[] createNodes(EngineLocation key) {
            return new Node[] { new EngineNode(le.provideEngineAt(key), key)};
        }

        public void stateChanged(ChangeEvent e) {
            setKeys(le.provideSortLocations());
        }
        
    }

}
