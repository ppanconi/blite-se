/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.lang;

import org.openide.awt.StatusDisplayer;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

public final class UndeployAction extends CookieAction {

    protected void performAction(Node[] activatedNodes) {
        BliteDataObject bliteDataObject = activatedNodes[0].getLookup().lookup(BliteDataObject.class);
        BliteEnvProviderService envService = Lookup.getDefault().lookup(BliteEnvProviderService.class);
        BliteDefModelProvider mp = activatedNodes[0].getLookup().lookup(BliteDefModelProvider.class);

        if (envService != null) {
            envService.remove(bliteDataObject);
            mp.fireActionsChange();

            StatusDisplayer.getDefault().setStatusText("File '" + bliteDataObject.getName() + "' undeployed successfully");
        }
    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(UndeployAction.class, "CTL_UndeployAction");
    }

    protected Class[] cookieClasses() {
        return new Class[]{BliteDataObject.class};
    }

    @Override
    protected String iconResource() {
        return "it/unifi/dsi/blide/lang/undeploy.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return true;
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        boolean enab = super.enable(activatedNodes);

        if (enab) {
            BliteEnvProviderService localProvider = Lookup.getDefault().lookup(BliteEnvProviderService.class);
            BliteDataObject bliteDataObject = activatedNodes[0].getLookup().lookup(BliteDataObject.class);
            if (null == localProvider) {
                enab = false;
            } else {
                if (!localProvider.isInstaled(bliteDataObject)) {
                    enab = false;
                }
            }
        }

        return enab;
    }
}

