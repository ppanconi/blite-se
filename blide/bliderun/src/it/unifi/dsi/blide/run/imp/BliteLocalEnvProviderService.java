/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blide.lang.BliteDataObject;
import it.unifi.dsi.blide.lang.BliteEnvProviderService;
import it.unifi.dsi.blide.lang.BliteIncompatibleUnitException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.filesystems.FileStateInvalidException;

/**
 *
 * @author panks
 */
public class BliteLocalEnvProviderService implements BliteEnvProviderService {

    private Map<URL, Object> deployed = new HashMap<URL, Object>();

    public boolean isInstaled(BliteDataObject dataObject) {
        try {
            URL url = dataObject.getPrimaryFile().getURL();
            return (null != deployed.get(url));
        } catch (FileStateInvalidException ex) {
            Logger.getLogger(BliteLocalEnvProviderService.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    public void deploy(BliteDataObject dataObject) throws BliteIncompatibleUnitException {
        try {
            URL url = dataObject.getPrimaryFile().getURL();
            deployed.put(url, dataObject);

            System.out.println("Deployed " + url);
        } catch (FileStateInvalidException ex) {
            Logger.getLogger(BliteLocalEnvProviderService.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    public void remove(BliteDataObject dataObject) {
        try {
            URL url = dataObject.getPrimaryFile().getURL();
            deployed.remove(url);

            System.out.println("Undeployed " + url);
        } catch (FileStateInvalidException ex) {
            Logger.getLogger(BliteLocalEnvProviderService.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

}
