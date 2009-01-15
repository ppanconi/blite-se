/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.localenv.LocalDefinitionMonitor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author panks
 */
public class DefinitionNode extends AbstractNode {

    static final String ICON_DEF = "it/unifi/dsi/blide/run/imp/defnode.png";
    static final String ICON_RUN = "it/unifi/dsi/blide/run/imp/defnodeRun.png";

    private boolean isReadyToRun = false;

    public DefinitionNode(Engine engine, BliteDeploymentDefinition definition) {
        super(new DefinitionChildrens(engine, definition), Lookups.fixed(engine, definition));

        String desc = "" + definition.getBliteId();
        String name = "Definition " + desc.substring(desc.lastIndexOf("/") + 1);

        setName(name);
        setShortDescription(desc);

        if (definition.provideServiceInstance() != null) {
            isReadyToRun = true;
            setIconBaseWithExtension(ICON_RUN);
        } else {
            isReadyToRun = false;
            setIconBaseWithExtension(ICON_DEF);
        }

    }

    public BliteDeploymentDefinition getDefinition() {
        return getLookup().lookup(BliteDeploymentDefinition.class);
    }

    @Override
    public Action[] getActions(boolean context) {
        Action[] acts = super.getActions(context);

        if (isReadyToRun) {

            //TODO Find the action in same lookup...
            Action runAct = RunOneDefAction.get(RunOneDefAction.class);

            if (acts != null) {
                List<Action> l = Arrays.asList(acts);

                l = new ArrayList<Action>(l);
                l.add(runAct);

                acts = l.toArray(acts);
            } else {
                acts = new Action[] {runAct};
            }
        }

        return acts;
    }

    private static class DefinitionChildrens extends Children.Keys<ProcessInstance>
            implements ChangeListener {

        private Engine engine;
        private BliteDeploymentDefinition definition;
        private LocalDefinitionMonitor monitor;

        public DefinitionChildrens(Engine engine, BliteDeploymentDefinition definition) {
            this.engine = engine;
            this.definition = definition;
            this.monitor = (LocalDefinitionMonitor) engine.getMonitor(definition.getBliteId());
            monitor.addChangeListener(WeakListeners.change(this, monitor));
        }

        @Override
        protected void addNotify() {
            setKeys(monitor.getInstances());
        }



        @Override
        protected Node[] createNodes(ProcessInstance key) {
            return new Node[] {new InstanceNode(key)};
        }

        public void stateChanged(ChangeEvent e) {
            setKeys(monitor.getInstances());
        }

    }

    

}
