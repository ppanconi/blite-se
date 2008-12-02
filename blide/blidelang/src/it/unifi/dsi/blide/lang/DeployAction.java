/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.lang;

import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

public final class DeployAction extends CookieAction {

    protected void performAction(Node[] activatedNodes) {
        BliteDataObject bliteDataObject = activatedNodes[0].getLookup().lookup(BliteDataObject.class);

        BliteEnvProviderService envService = Lookup.getDefault().lookup(BliteEnvProviderService.class);

        if (envService != null) {
            try {
                envService.deploy(bliteDataObject);
            } catch (BliteIncompatibleUnitException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
   
    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(DeployAction.class, "CTL_DeployAction");
    }

    protected Class[] cookieClasses() {
        return new Class[]{BliteDataObject.class};
    }

    @Override
    protected String iconResource() {
        return "it/unifi/dsi/blide/lang/deploy.png";
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
            if (null == Lookup.getDefault().lookup(BliteEnvProviderService.class))
                enab = false;
            else {
                BliteDefModelProvider mp = activatedNodes[0].getLookup().lookup(BliteDefModelProvider.class);

                if (mp.getDefinitionModel() == null)
                    enab = false;
            }
        }

        return enab;
    }


}

