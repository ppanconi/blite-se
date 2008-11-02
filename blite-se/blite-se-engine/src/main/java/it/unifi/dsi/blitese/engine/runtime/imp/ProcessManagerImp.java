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
package it.unifi.dsi.blitese.engine.runtime.imp;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.InComingEventKey;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.ProcessManager; 
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;
import it.unifi.dsi.blitese.engine.runtime.VariableScope;
import it.unifi.dsi.blitese.parser.ABltValueHolder;
import it.unifi.dsi.blitese.parser.BLTDEFInvPartners;
import it.unifi.dsi.blitese.parser.BLTDEFReceiveActivity;
import it.unifi.dsi.blitese.parser.BLTDEFServiceInstance;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *
 * @author panks
 */

public class ProcessManagerImp implements ProcessManager {
    
    private static final Logger LOGGER = Logger.getLogger(ProcessManagerImp.class.getName());
    
    private BliteDeploymentDefinition mBliteProcessDef;
    private Engine mEngine;
    private String mSaName;
    private String mSuName;
    
    private Object definitionProcessLevelLock = new Object();
    //
    private Map<String, ProcessInstance> mInstances = new HashMap<String, ProcessInstance>();
    private HashMap<String, List<BLTDEFReceiveActivity>> mPortIdToPortDef = new HashMap<String, List<BLTDEFReceiveActivity>>();

    //This not null only if the definition is readyToRun type
    private BLTDEFServiceInstance readyToRunInstance;
    
    public ProcessManagerImp(BliteDeploymentDefinition bliteProcessDef, Engine engine, String saName, String suName) {
        mBliteProcessDef = bliteProcessDef;
        mEngine = engine;
        
        mSaName = (saName != null) ? saName : "unavailable";
	mSuName = (suName != null) ? suName : "unavailable";

        //we record the mapping
        mPortIdToPortDef = bliteProcessDef.getServiceElement().getPortMapping();
        
        //if the static definition conteins same ready to run instance
        //we start with it
        readyToRunInstance = bliteProcessDef.provideServiceInstance();
        
    }
    
    public Object startReadyToRunDefinition() {
        if (readyToRunInstance != null) {
            ProcessInstance instance = createInstance();
            LOGGER.info("Created deploy ready to run instance: " + instance.getInstanceId());
            instance.activete();
            return instance.getInstanceId();
        }
        return null;
    }


    public ServiceIdentifier resovleParterLink(BLTDEFInvPartners partnersDef, VariableScope variableScope) {
        
        ABltValueHolder vh = partnersDef.getOther();

        //NOTE TO EXPLORE THIS OPERATION
        //JUST A SIMPLE SOLUTION
        return new ServiceIdentifier(null, RuntimeValueFactory.makeRuntimeValue(vh, variableScope).toString());
    }

    public Object getDefinitionProcessLevelLock() {
        return definitionProcessLevelLock;
    }

    public Engine getEngine() {
        return mEngine;
    }

    public InComingEventKey invoke(ServiceIdentifier serviceId, String operation, MessageContainer messageContainer, ProcessInstance instance) {
//             return mEngine.getChannel()
//                    .invoke(serviceId, operation, messageContainer, instance);
        
        return mEngine.invoke(serviceId, operation, messageContainer, instance);
    }

    public MessageContainer cosumeEvent(InComingEventKey inComingEventKey) {
        return mEngine.cosumeEvent(inComingEventKey);
    }

    public void consumeEvent(InComingEventKey inComingEventKey, MessageContainer messageContainer) {
        mEngine.consumeEvent(inComingEventKey, messageContainer);
    }

    public List<MessageContainer> provideEvents(InComingEventKey inComingEventKey) {
        
        LOGGER.log(Level.INFO, "LOOKING for Event Subject with key " + inComingEventKey.hashCode());
        
        return mEngine.provideEvents(inComingEventKey);
    }
    
    

    ////////////////////////////////////////////////////////////////////////////
    // Utility 
    private Long instNumber = 0L;
    synchronized  private ProcessInstance createInstance() {
        ProcessInstance i = new ProcessInstanceImp(mEngine, this, "" + mBliteProcessDef.getBliteId() + ":" + instNumber++ , mBliteProcessDef);
        mInstances.put(i.getInstanceId(), i);
        return i;
    }

    public void manageRequest(ServiceIdentifier serviceId, String operation, MessageContainer messageContainer) {
        
        //we locate the target port for this request
        String portId = BLTDEFReceiveActivity.makePortId(serviceId.provideStringServiceName(), operation);
        Object[] parts = messageContainer.getContent().getParts();
        
        if (!mPortIdToPortDef.containsKey(portId)) {
            LOGGER.info("INVALID portId " + portId + " for service");
            //we report the error
            Object exchangeId = messageContainer.getId();
            String message = "Invalid destination port " + portId;
            mEngine.sendResponseErrorStatus(message, exchangeId);
            
            return;
        } else {
            //we test the conformity
            BLTDEFReceiveActivity recDef = mPortIdToPortDef.get(portId).get(0);
            
            if (recDef.getParams().provideFormalParamN() != parts.length) {
                LOGGER.info("INVALID request for portId " + portId + " mismatch in the number of params");
                //we report the error
                Object exchangeId = messageContainer.getId();
                String message = "Invalid request for portId " + portId + ", mismatch in the number of params";
                mEngine.sendResponseErrorStatus(message, exchangeId);
            
                return;
            }
        }
        
        RequestInComingEventKey icek = 
                    InComingEventKeyFactory.createRequestInComingEventKey(serviceId, portId);
        icek.setMc(messageContainer);        
            
        LOGGER.info("Managing Request to port " + portId);
        
        //test if we target a create instance
        if (mBliteProcessDef.getServiceElement().isCreateInstancePort(portId)) {
            //we create a new instance

            ProcessInstance pi = createInstance();
            
            LOGGER.info("Created new instance with id " + pi.getInstanceId() + " for request to port " + portId);
            
            synchronized (getDefinitionProcessLevelLock()) {
                getEngine().addEventSubjet(icek, messageContainer);
            }
            
            pi.activete();
            
        } else {
            //we notify the incoming event
            
            synchronized (getDefinitionProcessLevelLock()) {
                
                getEngine().addEventSubjet(icek, messageContainer);
                getEngine().resumeFlowWaitingEvent(icek);
                
            }        
            
        }
        
        //we send the done the one-way protocol is done.
        mEngine.sendResponseDoneStatus(icek, messageContainer.getId());
        
    }

    
    
}

