/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blitese.localenv.EngineLocation;
import it.unifi.dsi.blitese.localenv.LocalEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
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

    @Override
    public Action[] getActions(boolean context) {
        Action[] acts = super.getActions(context);

        Action act = CleanAction.get(CleanAction.class);

        if (acts != null) {
            List<Action> l = Arrays.asList(acts);

            l = new ArrayList<Action>(l);
            l.add(act);

            acts = l.toArray(acts);
        } else {
            acts = new Action[] {act};
        }

        return acts;
    }

    private static class CleanAction extends CookieAction  {

        @Override
        protected int mode() {
            return CookieAction.MODE_EXACTLY_ONE;
        }

        @Override
        protected Class<?>[] cookieClasses() {
           return new Class[]{LocalEnvironment.class};
        }

        @Override
        protected void performAction(Node[] activatedNodes) {
            BliteLocalEnvTopComponent.findInstance().getEnvironment().clear();
        }

        @Override
        public String getName() {
            return "Clean All";
        }


        public HelpCtx getHelpCtx() {
            return HelpCtx.DEFAULT_HELP;
        }

        @Override
        protected boolean asynchronous() {
            return false;
        }

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
            return new Node[] { new EngineNode(le, le.provideEngineAt(key), key)};
        }

        public void stateChanged(ChangeEvent e) {
            setKeys(le.provideSortLocations());
        }
        
    }

}
