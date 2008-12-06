/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
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
        super(Children.LEAF, Lookups.fixed(engine, definition));

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



}
