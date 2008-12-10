/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.localenv;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.runtime.DefinitionMonitor;
import it.unifi.dsi.blitese.engine.runtime.InstanceMonitor;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author panks
 */
public class LocalDefinitionMonitor implements DefinitionMonitor {

    private BliteDeploymentDefinition definition;

    private List<ProcessInstance> instances = new ArrayList<ProcessInstance>();
    private Map<String, InstanceMonitor> mInsToInstMonitor = new HashMap<String, InstanceMonitor>();

    public LocalDefinitionMonitor(BliteDeploymentDefinition definition) {
        this.definition = definition;
    }

    public BliteDeploymentDefinition getDefinition() {
        return definition;
    }

    public InstanceMonitor instanceCreate(ProcessInstance i) {
        instances.add(i);

        LocalInstanceMonitor im = new LocalInstanceMonitor(i);
        mInsToInstMonitor.put(i.getInstanceId(), im);
        i.setMonitor(im);
        //-- we have to notifay the instance creation
        fire();
        
        return im;
    }

    public List<ProcessInstance> getInstances() {
        return instances;
    }

    public InstanceMonitor getInstanceMonitor(String instaceId) {
        return mInsToInstMonitor.get(instaceId);
    }

    // -------------------------------------------------------------------------
    // We have a series of listeners lisstening to this monitor
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

}
