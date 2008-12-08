/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.runtime.DefinitionMonitor;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.InstanceMonitor;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
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

        setName("" + definition.getBliteId());

        if (definition.provideServiceInstance() != null) {
            isReadyToRun = true;
            setIconBaseWithExtension(ICON_RUN);
        } else {
            isReadyToRun = false;
            setIconBaseWithExtension(ICON_DEF);
        }

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
            implements DefinitionMonitor {

        private Engine engine;
        
        private BliteDeploymentDefinition definition;

        private List<ProcessInstance> instances = new ArrayList<ProcessInstance>();

        private java.util.Map<Object, InstanceNode> mNodes = new HashMap<Object, InstanceNode>();

        public DefinitionChildrens(Engine engine, BliteDeploymentDefinition definition) {
            this.engine = engine;
            this.definition = definition;
            engine.setMonitor(this);
        }

        @Override
        protected Node[] createNodes(ProcessInstance key) {
            return new Node[] {mNodes.get(key.getInstanceId())};
        }

        public BliteDeploymentDefinition getDefinition() {
            return definition;
        }

        public InstanceMonitor instanceCreate(ProcessInstance newInstance) {

            instances.add(newInstance);
            InstanceNode node = new InstanceNode(newInstance);
            mNodes.put(newInstance.getInstanceId(), node);

            setKeys(instances);

            return node;
        }

    }

    public BliteDeploymentDefinition getDefinition() {
        return getLookup().lookup(BliteDeploymentDefinition.class);
    }

}
