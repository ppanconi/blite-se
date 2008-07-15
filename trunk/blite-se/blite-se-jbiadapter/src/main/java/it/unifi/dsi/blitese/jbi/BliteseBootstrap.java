/*
 *  DO NOT EDIT
 * 
 *  The contents of this file are subject to the terms
 *  of the GNU General Public License v3
 *  You may not use this file except
 *  in compliance with the License.
 * 
 *  You can obtain a copy of the license at
 *  http://www.gnu.org/licenses/gpl.html
 *  See the License for the specific language governing
 *  permissions and limitations under the License.
 * 
 */

package it.unifi.dsi.blitese.jbi;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jbi.JBIException;
import javax.jbi.component.Bootstrap;
import javax.jbi.component.ComponentContext;
import javax.jbi.component.InstallationContext;
import javax.jbi.management.MBeanNames;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 *
 * @author panks
 */
public class BliteseBootstrap implements Bootstrap {
    private static Logger LOGGER = Logger.getLogger(BliteseBootstrap.class.getName());
    private ObjectName mInstallerConfigMBeanName;
    private BliteseInstallerConfiguration mConfigMBean;
    private String workspaceRoot;
    private MBeanServer mMBeanServer;
//    private ComponentConfig props;

    /**
     * default constructor
     */
    public BliteseBootstrap() {
    }

    /**
     * Initializes the installation environment for a SE. This method is expected to save any
     * information from the installation context that may be needed by other methods.
     *
     * @param installContext is the context containing information from the install command and
     *        from the SE jar file.
     *
     * @throws javax.jbi.JBIException 
     * @throws JBIException when there is an error requiring that the installation be terminated.
     */
    public void init(InstallationContext installContext)
        throws javax.jbi.JBIException {
        
        ComponentContext ctx = installContext.getContext();
        
        if (LOGGER.isLoggable(Level.FINE)) {
        	//LOGGER.log(Level.FINE, I18n.loc("BPJBI-3000: Calling init method"));
        	LOGGER.log(Level.FINE, "BPJBI-3000: Calling init method");
        }

        mMBeanServer = ctx.getMBeanServer();
    
        MBeanNames mbeanNames = installContext.getContext().getMBeanNames();
        mInstallerConfigMBeanName = mbeanNames.createCustomComponentMBeanName(MBeanNames.BOOTSTRAP_EXTENSION);
        workspaceRoot = ctx.getWorkspaceRoot();

        try {
            //read the values from component configuration
            //props = ComponentConfig.parse(installContext.getInstallRoot());
            mConfigMBean = new BliteseInstallerConfiguration();
            //mConfigMBean.setValues(props);
            
            if(mMBeanServer != null) {
                // register MBean only if it is not already registered.
                if(mMBeanServer.isRegistered(mInstallerConfigMBeanName) == false) {
                    mMBeanServer.registerMBean(mConfigMBean, mInstallerConfigMBeanName);
                }
            }         
        } catch (Exception ex) {
//            throw new JBIException(I18n.loc("BPJBI-7000: caught exception while creating Installation " + 
//            		"Configuration MBean, failed to init Blitese component"), ex);
        	throw new JBIException("BPJBI-7000: caught exception while creating Installation " + 
        			"Configuration MBean, failed to init Blitese component", ex);
        }
    }

    /**
     * Obtains the optional installer configuration MBean ObjectName. If none is provided by this
     * SE, returns null.
     * 
     * @return ObjectName which represents the MBean registered by the init() method. If none was
     *         registered, returns null.
     */
    public ObjectName getExtensionMBeanName() {
        LOGGER.log(Level.INFO, "BliteseBootstrap.retrieving_installation_configuration_mbean");

        return mInstallerConfigMBeanName;
    }

    /**
     * Called at the beginning of installation of a SE to perform any special installation tasks
     * required by the SE.
     *
     * @throws javax.jbi.JBIException DOCUMENT ME!
     * @throws JBIException when there is an error requiring that the installation be terminated.
     */
    public void onInstall() throws javax.jbi.JBIException {
        LOGGER.log(Level.INFO, "BliteseBootstrap.Calling_onInstall_method");
        
        //read the values from MBean & persist them to workspace folder, 
        //on ComponentLifeCycle.init()these values are loaded 
//        props = mConfigMBean.getValues();
//        ConfigPersistence.persistConfig(props, workspaceRoot);
        
        LOGGER.log(Level.INFO, "BliteseBootstrap.onInstall_method_has_been_called");
    }

    /**
     * Called at the beginning of uninstallation of a SE to perform any special uninstallation tasks
     * required by the SE.
     * 
     * @throws javax.jbi.JBIException when there is an error requiring that the uninstallation be
     *             terminated.
     */
    public void onUninstall() throws javax.jbi.JBIException {
        LOGGER.log(Level.INFO, "BliteseBootstrap.Calling_onUninstall_method");
        LOGGER.log(Level.INFO, "BliteseBootstrap.onUninstall_method_has_been_called");
    }

    /**
     * Cleans up any resources allocated by the bootstrap implementation, including deregistration
     * of the extension MBean, if applicable. This method will be called after the onInstall() or
     * onUninstall() method is called, whether it succeeds or fails.
     *
     * @throws JBIException when cleanup processing fails to complete successfully.
     */
    public void cleanUp() throws JBIException {
        LOGGER.log(Level.INFO, "BliteseBootstrap.Calling_cleanUp_method");
        try {
            mMBeanServer.unregisterMBean(mInstallerConfigMBeanName);
        } catch (Exception ex) {
//            throw new JBIException(I18n.loc("BPJBI-7000: caught exception while creating Installation " + 
//    			"Configuration MBean, failed to init Blitese component"), ex);
        	throw new JBIException("BPJBI-7000: caught exception while creating Installation " + 
        			"Configuration MBean, failed to init Blitese component", ex);
        }
        LOGGER.log(Level.INFO, "BliteseBootstrap.cleanUp_method_has_been_called");
    }
}
