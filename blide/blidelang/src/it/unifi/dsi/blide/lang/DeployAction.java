/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.lang;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

public final class DeployAction extends CookieAction {

    protected void performAction(Node[] activatedNodes) {
        BliteDataObject bliteDataObject = activatedNodes[0].getLookup().lookup(BliteDataObject.class);

        BliteEnvProviderService envService = Lookup.getDefault().lookup(BliteEnvProviderService.class);
//        BliteDataNode bliteDataNode = activatedNodes[0].getLookup().lookup(BliteDataNode.class);
        BliteDefModelProvider mp = activatedNodes[0].getLookup().lookup(BliteDefModelProvider.class);

        if (envService != null) {
            try {
                envService.deploy(bliteDataObject);
                mp.fireActionsChange();

                StatusDisplayer.getDefault().setStatusText("File '" + bliteDataObject.getName() + "' deployed successfully");
            } catch (BliteIncompatibleUnitException ex) {

                NotifyDescriptor d = new NotifyDescriptor.
                        Message("Impossible to deploy Blite definition \"" + bliteDataObject.getName() + "\": " + ex.getMessage() +
                        "\nRename the services, compile and try to redeploy.", NotifyDescriptor.ERROR_MESSAGE);
                DialogDisplayer.getDefault().notify(d);
                //Exceptions.printStackTrace(ex);
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

