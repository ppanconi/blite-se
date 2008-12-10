/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.run.imp;

import it.unifi.dsi.blide.lang.BliteDataObject;
import it.unifi.dsi.blide.lang.BliteDefModelProvider;
import it.unifi.dsi.blide.lang.BliteEnvProviderService;
import it.unifi.dsi.blide.lang.BliteIncompatibleUnitException;
import it.unifi.dsi.blitese.localenv.IncompatibleCompUnitException;
import it.unifi.dsi.blitese.localenv.LocalEnvironment;
import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import java.net.URL;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.util.Exceptions;

/**
 *
 * @author panks
 */
public class BliteLocalEnvProviderService implements BliteEnvProviderService {

    private LocalEnvironment localEnviroment = BliteLocalEnvTopComponent.findInstance().getEnvironment();

    public LocalEnvironment getLocalEnvironment() {
        return localEnviroment;
    }

    // -------------------------------------------------------------------------

    public boolean isInstaled(BliteDataObject dataObject) {
        try {
            URL url = dataObject.getPrimaryFile().getURL();
            return null != localEnviroment.getInsatlledUnit(url);
        } catch (FileStateInvalidException ex) {
            Exceptions.printStackTrace(ex);
            throw new RuntimeException(ex);
        }
    }

    public void deploy(BliteDataObject dataObject) throws BliteIncompatibleUnitException {

        BliteDefModelProvider modelProvider =
                    dataObject.getNodeDelegate().getLookup().lookup(BliteDefModelProvider.class);

        BLTDEFCompilationUnit cu = modelProvider.getDefinitionModel();

        if (cu != null) {
            try {
                URL url = dataObject.getPrimaryFile().getURL();
                cu.setResource(url);

                localEnviroment.synchCompilationUnit(cu);

            } catch (IncompatibleCompUnitException ex) {

                throw new BliteIncompatibleUnitException(ex.getMessage());

            } catch (FileStateInvalidException ex) {
                Exceptions.printStackTrace(ex);
                throw new RuntimeException(ex);
            }
        }
    }

    public void remove(BliteDataObject dataObject) {
        BliteDefModelProvider modelProvider =
                    dataObject.getNodeDelegate().getLookup().lookup(BliteDefModelProvider.class);

        BLTDEFCompilationUnit cu = modelProvider.getDefinitionModel();

        if (cu != null) {
            try {
                URL url = dataObject.getPrimaryFile().getURL();
                cu.setResource(url);

                localEnviroment.removeCompilationUnit(cu);

            } catch (FileStateInvalidException ex) {
                Exceptions.printStackTrace(ex);
                throw new RuntimeException(ex);
            }
        }

    }


}
