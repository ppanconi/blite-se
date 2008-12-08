/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.localenv;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author panks
 */
public class SingleStepper {

    private boolean enabled = false;
    private boolean steped = false;

    final private Object REQUEST = new Object();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSteped() {
        return steped;
    }

    synchronized public void step() {

        synchronized (REQUEST) {

            steped = true;
            fire();
            
            try {
                REQUEST.wait();
            } catch (InterruptedException ex) {
            }
        }

    }

    public void letgo() {

        if (steped) {
            synchronized (REQUEST) {
                steped = false;
                REQUEST.notify();
            }
        }
    }

    // -------------------------------------------------------------------------
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
