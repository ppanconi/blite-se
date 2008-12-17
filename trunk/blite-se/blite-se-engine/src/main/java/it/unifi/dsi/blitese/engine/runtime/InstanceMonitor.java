/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.engine.runtime;

/**
 * This's an interface to comunicate the Process Instance lifecycle
 * and flow execition to possible observers and auditer.
 *
 * @author panks
 */
public interface InstanceMonitor {

    public void stateChanged();

    public void activityStep(ActivityComponent activity, FlowOwner flowOwner);
}
