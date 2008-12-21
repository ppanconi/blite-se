/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.localenv;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.FlowOwner;
import it.unifi.dsi.blitese.engine.runtime.InstanceMonitor;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author panks
 */
public class LocalInstanceMonitor implements InstanceMonitor {

    private ProcessInstance instance;

    public LocalInstanceMonitor(ProcessInstance instance) {
        this.instance = instance;
    }

    public ProcessInstance getInstance() {
        return instance;
    }

    public void stateChanged() {
        fire();
    }

    // -------------------------------------------------------------------------
    // We have a series of listeners listening to this monitor
    private static List <ChangeListener> listeners =
            Collections.<ChangeListener>synchronizedList(new
            LinkedList <ChangeListener>());
    public void addChangeListener (ChangeListener l) {
        listeners.add (l);
    }

    public void removeChangeListener (ChangeListener l) {
        listeners.remove (l);
    }

    private void fire() {
        ChangeListener[] l = listeners.toArray (new ChangeListener[0]);
        for (int i=0; i < l.length; i++) {
            l[i].stateChanged (new ChangeEvent (this));
        }
    }

    private List<ActivityComponent> execution = new LinkedList<ActivityComponent>();

    public void activityStep(ActivityComponent activity, FlowOwner flowOwner) {
        execution.add(activity);
    }

    public List<ActivityComponent> getExecution() {
        return execution;
    }

}
