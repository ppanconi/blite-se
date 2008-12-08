/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.engine.runtime;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;

/**
 * This's an interface to comunicate the Definition lifecycle
 * to possible observers and auditer.
 *
 *
 *
 * @author panks
 */
public interface DefinitionMonitor {

    public BliteDeploymentDefinition getDefinition();

    public InstanceMonitor instanceCreate(ProcessInstance instance);
    
}
