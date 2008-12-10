/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.imp.ABaseContext.ContextState;
import it.unifi.dsi.blitese.engine.runtime.imp.ProcessInstanceImp;
import it.unifi.dsi.blitese.localenv.LocalInstanceMonitor;
import java.awt.Image;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author panks
 */
public class InstanceNode extends AbstractNode implements ChangeListener {

    static final String ICON_PATH = "it/unifi/dsi/blide/run/imp/instdef.png";

    static final String OK_PATH = "it/unifi/dsi/blide/run/imp/inst-ok.png";
    static final String ER_PATH = "it/unifi/dsi/blide/run/imp/inst-error.png";
    static final String WR_PATH = "it/unifi/dsi/blide/run/imp/inst-term.png";

    private LocalInstanceMonitor monitor;


    public InstanceNode(ProcessInstance instance) {
        super(Children.LEAF, Lookups.fixed(instance));

        monitor = (LocalInstanceMonitor) instance.getMonitor();
        if (monitor != null) {
            monitor.addChangeListener(WeakListeners.change(this, monitor));
        }

        String id = instance.getInstanceId();
        String name = "Instance " + id.substring(id.indexOf("::") + 2, id.length());

        setName(name);
        setIconBaseWithExtension(ICON_PATH);
    }

    public ProcessInstance getInstance() {
        return getLookup().lookup(ProcessInstance.class);
    }

    public void stateChanged(ChangeEvent e) {
        fireIconChange();
    }

    @Override
    public Image getIcon(int type) {
        Image original = super.getIcon(type);

        ProcessInstanceImp ii = (ProcessInstanceImp) getInstance();
        ContextState istate = ii.getState();

        if (istate == ContextState.FAULTED) {

            Image errorBadge = ImageUtilities.loadImage(ER_PATH);
            Image badged = ImageUtilities.mergeImages(original, errorBadge, 0, 7);

            return badged;

        } if (istate == ContextState.COMPLETED) {

            Image errorBadge = ImageUtilities.loadImage(OK_PATH);
            Image badged = ImageUtilities.mergeImages(original, errorBadge, 0, 7);

            return badged;
            
        } if (istate == ContextState.TEMINATED) {

            Image errorBadge = ImageUtilities.loadImage(WR_PATH);
            Image badged = ImageUtilities.mergeImages(original, errorBadge, 0, 7);

            return badged;

        } else {
            return original;
        }
    }

}
