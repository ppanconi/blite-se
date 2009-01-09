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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.WeakListeners;
import org.openide.util.datatransfer.ExTransferable;
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
        super(Children.LEAF, Lookups.fixed(instance, instance.getMonitor()));

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

        }
        if (istate == ContextState.COMPLETED) {

            Image errorBadge = ImageUtilities.loadImage(OK_PATH);
            Image badged = ImageUtilities.mergeImages(original, errorBadge, 0, 7);

            return badged;

        }
        if (istate == ContextState.TEMINATED) {

            Image errorBadge = ImageUtilities.loadImage(WR_PATH);
            Image badged = ImageUtilities.mergeImages(original, errorBadge, 0, 7);

            return badged;

        } else {
            return original;
        }
    }

    @Override
    public Action[] getActions(boolean context) {
        Action[] acts = super.getActions(context);

        Action act = ViewInstanceAction.get(ViewInstanceAction.class);

        if (acts != null) {
            List<Action> l = Arrays.asList(acts);

            l = new ArrayList<Action>(l);
            l.add(act);

            acts = l.toArray(acts);
        } else {
            acts = new Action[]{act};
        }

        return acts;
    }

    @Override
    public Action getPreferredAction() {
        Action act = ViewInstanceAction.get(ViewInstanceAction.class);
        return act;
    }

    public static DataFlavor INSTANCE_MONITOR_FLAVOR = new DataFlavor(LocalInstanceMonitor.class, "LocalInstanceMonitor");
    @Override
    public Transferable drag() throws IOException {
        ExTransferable retValue = ExTransferable.create( super.drag() );
        //add the 'data' into the Transferable
        retValue.put( new ExTransferable.Single(INSTANCE_MONITOR_FLAVOR) {
            protected Object getData() throws IOException, UnsupportedFlavorException {
                return monitor;
            }
        });
        return retValue;
    }



}
