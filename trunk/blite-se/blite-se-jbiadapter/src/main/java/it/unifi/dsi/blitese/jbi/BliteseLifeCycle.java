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
import javax.jbi.component.Component;
import javax.jbi.component.ComponentContext;
import javax.jbi.component.ComponentLifeCycle;
import javax.jbi.component.ServiceUnitManager;
import javax.jbi.messaging.DeliveryChannel;

/**
 *
 * @author panks
 */
public class BliteseLifeCycle //implements ComponentLifeCycle
        //, Component
        //, NotificationListener
{
/*
    private static Logger LOGGER = Logger.getLogger(BliteseLifeCycle.class.getName());
    private Engine mEngine;
    private ComponentContext mContext;
    private DeliveryChannel mChannel;
    private EngineChannel mEngineChannel;
    private ServiceUnitManager mServiceUnitMgr;
//    private EndpointStatisticsHelper mEpsHelper;
    private DeploymentBindings mDepBindings;
//    private RuntimeConfigurationHelper mRuntimeConfigHelper;
//    private BPELSERuntimeConfiguration mConfigMBean;
//    private ManagementMbeanRegister mManagementMbeanRegister;
//    private BPELSEManagementMBean mManagementMbean;
//    private ManagementMbeanRegister mExtendedMgmtActionsMbeanRegister;
//    private ActionableMBean mActionableMbean;
    
    private int mThreadCount;
    private BPELSEHelper threadObj;
    private List mThreadsList;
 */

    /*
     * ComponentLifeCycle interface implementation
     */
    
    /**
     * Initialize the SE. This performs initialization required by the SE but does not make it
     * ready to process messages. This method is called once for each life cycle of the SE.
     *
     * @param context the engine context.
     *
     * @throws javax.jbi.JBIException if the SE is unable to initialize.
     */
    public void init(ComponentContext context) throws javax.jbi.JBIException {        
        /*
        mContext = context;
        
        if (LOGGER.isLoggable(Level.FINE)) {
        	LOGGER.log(Level.FINE, "BPJBI-3024: Initializing Blite service engine");
        }

        // incomplete works, it needs more runtime configuration helper works
//        try {
//            
//            mRuntimeConfigHelper = new RuntimeConfigurationHelper(
//                    context.getMBeanNames().createCustomComponentMBeanName("Configuration"), context.getMBeanServer());
//            mManagementMbeanRegister = new ManagementMbeanRegister( context.getMBeanNames().createCustomComponentMBeanName("Administration"), 
//                    context.getMBeanServer());
//            mExtendedMgmtActionsMbeanRegister = new ManagementMbeanRegister( context.getMBeanNames().createCustomComponentMBeanName("ManagementActions"), 
//                    context.getMBeanServer());
//            
//            mConfigMBean = new BPELSERuntimeConfiguration(context);
//            
//            mRuntimeConfigHelper.registerMBean(mConfigMBean);
//        } 
//        catch (Exception ex) {
//            throw new JBIException(I18n.loc("BPJBI-7018: Unable to register mbean"), ex);
//        }        
//        
//        // Register handle configuration change notifications
//        mConfigMBean.addNotificationListener(this, null, null);

//        StatusProviderHelper statusProviderHelper = null;

//        try {
//            statusProviderHelper = new StatusProviderHelper(
//            		I18n.loc("BPJBI-5000: BPELSE Status"), 
//                    context.getMBeanNames().createCustomComponentMBeanName("Statistics"),
//                    context.getMBeanServer());
//            statusProviderHelper.registerMBean();
//            LOGGER.log(Level.CONFIG, I18n.loc("BPJBI-4014: Registered Status Provider MBean for {0}", 
//            		context.getComponentName()));
//
//        } catch (Exception ex) {
//            LOGGER.log(Level.WARNING, I18n.loc("BPJBI-6013: Failed to register status provider MBean"), ex);
//            throw new JBIException(I18n.loc("BPJBI-6013: Failed to register status provider MBean"), ex);
//        }

        threadObj = new BPELSEHelper();

        mEngineChannel = new EngineChannel(threadObj);

        BPELSERegistry registry = BPELSERegistry.getInstance();
        
        //Register the transaction manager
//        registry.register(TransactionManager.class.getName(), mContext.getTransactionManager());
        
        // create the ConnectionConfiguration and set it on the Engine.
        ConnectionConfiguration connConfig = new ConnectionConfigurationImpl(context, mConfigMBean.getProperties());

        try {
        	mEngine = new EngineImpl(mConfigMBean.getProperties(), mContext.getNamingContext());
        } catch (BPELConfigurationException e) {
            throw new JBIException(e);
        }
        
        mEngine.setOutChannel(mEngineChannel);
        mEngine.setConnectionConfiguration(connConfig);

        mEpsHelper = new EndpointStatisticsHelper();
        mEpsHelper.setStatusProviderHelper(statusProviderHelper);

        mDepBindings = new DeploymentBindings();

        mChannel = new BaseMessagingChannel(context);

        threadObj.setComponentContext(mContext);
        threadObj.setDeliveryChannel(mChannel);
        threadObj.setExchangeFactory(mChannel);
        threadObj.setEndpointStatisticsHelper(mEpsHelper);
        threadObj.setDeploymentBindings(mDepBindings);
        threadObj.mEngineChannel = mEngineChannel;      
        threadObj.mEngine = mEngine;

        mThreadCount = mConfigMBean.getThreadCount();
        
        //Register a XmlResourceProviderPool with the Registry. We use the name of
        //the interface XmlResourceProviderPool to register the pool.
        XmlResourceProviderPoolImpl xmlResProviderPool;
        try {
            xmlResProviderPool = new XmlResourceProviderPoolImpl(mThreadCount);
        } catch (Exception e) {
            throw new JBIException(e);
        }
        
        registry.register(XmlResourceProviderPool.class.getName(), xmlResProviderPool);
        
        mServiceUnitMgr = new BPELSEDeployer(mEngine, mContext, 
                                             mEpsHelper, mDepBindings);
        try {
            
            mManagementMbean = new BPELSEManagement (mEngine, this);        	
			mManagementMbeanRegister.registerMBean(mManagementMbean);
            
            mActionableMbean = new ActionableMBeanImpl(mEngine);
            mExtendedMgmtActionsMbeanRegister.registerMBean(mActionableMbean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new JBIException(I18n.loc("BPJBI-7018: Unable to register mbean"), e);
		} 
        
        LOGGER.log(Level.INFO, I18n.loc("BPJBI-5001: BPEL service engine initialized"));
        */
        /*try {
            mEngine.persist();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, 
                    MESSAGES.getString("BPELSELifeCycle.Failed_to_persist_engine_state"), ex);
            throw new JBIException(
                    MESSAGES.getString("BPELSELifeCycle.Failed_to_persist_engine_state"), ex);
        }*/
    }
    
}
