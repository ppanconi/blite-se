/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.parser.BLTDEFServiceInstance;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

public final class RunOneDefAction extends CookieAction {

    protected void performAction(Node[] activatedNodes) {
        BliteDeploymentDefinition def = activatedNodes[0].getLookup().lookup(BliteDeploymentDefinition.class);
        Engine engine = activatedNodes[0].getLookup().lookup(Engine.class);

        BLTDEFServiceInstance instDef = def.provideServiceInstance();

        if (def != null && engine != null && instDef != null) {
            engine.startReadyToRunDefinition(def.getBliteId());

            StatusDisplayer.getDefault().setStatusText("Started a new instance of " + def.getBliteId());
        }
    
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        boolean enab = super.enable(activatedNodes);

        if (enab) {
            BliteDeploymentDefinition def = activatedNodes[0].getLookup().lookup(BliteDeploymentDefinition.class);
            if (def == null || def.provideServiceInstance() == null)
                enab = false;
        }

        return enab;
    }



    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(RunOneDefAction.class, "CTL_RunOneDefAction");
    }

    protected Class[] cookieClasses() {
        return new Class[]{BliteDeploymentDefinition.class};
    }

    @Override
    protected String iconResource() {
        return "it/unifi/dsi/blide/run/imp/rundef.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}

