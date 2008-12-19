/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blide.run.api.InstanceVisualizer;
import it.unifi.dsi.blitese.engine.runtime.InstanceMonitor;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

public final class ViewInstanceAction extends CookieAction {

    protected void performAction(Node[] activatedNodes) {
        InstanceMonitor monitor = activatedNodes[0].getLookup().lookup(InstanceMonitor.class);

        InstanceVisualizer visualizer = Lookup.getDefault().lookup(InstanceVisualizer.class);
        if (visualizer != null && monitor != null) {
            visualizer.visualizeInstance(monitor);
        }
    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(ViewInstanceAction.class, "CTL_ViewInstanceAction");
    }

    protected Class[] cookieClasses() {
        return new Class[]{InstanceMonitor.class};
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        boolean enab = super.enable(activatedNodes);

        if (enab) {
            if (null == Lookup.getDefault().lookup(InstanceVisualizer.class))
                enab = false;
        }

        return enab;
    }

}

