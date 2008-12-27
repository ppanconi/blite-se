/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.lang;


/**
 *
 * @author panks
 */
public interface BliteEnvProviderService {

    public boolean isInstaled(BliteDataObject dataObject);

    public void deploy(BliteDataObject dataObject) throws BliteIncompatibleUnitException;

    public void remove(BliteDataObject dataObject);

}
