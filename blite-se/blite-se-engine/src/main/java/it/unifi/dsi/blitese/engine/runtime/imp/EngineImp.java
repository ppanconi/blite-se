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
import it.unifi.dsi.blitese.engine.runtime.DefinitionMonitor;
import it.unifi.dsi.blitese.engine.runtime.Engine; 
import it.unifi.dsi.blitese.engine.runtime.EngineChannel;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.InComingEventKey;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.MessageContent;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the implemetation of Bltie Engine the core of
 * blite-se.
 * 
 *
 * @author panks
 */

public class EngineImp implements Engine {
    
    private static final Logger LOGGER = Logger.getLogger(EngineImp.class.getName());
    
    private Object mDeployLock = new Object(); //onjext used as monitor in un/deployment phase
    private boolean mNewAddedDep = false; //one state marker for eventually persistence synch.
    private EngineChannel mChannel; //the comunication Channel
    
    private ConcurrentMap<InComingEventKey, List<FlowExecutor>>
                mEventWaitingExecutor = new ConcurrentHashMap<InComingEventKey, List<FlowExecutor>>();
    
    private ConcurrentHashMap<Object, ProcessManager>
                mMExchangeToProcessManager = new ConcurrentHashMap<Object, ProcessManager>();
    
    private ConcurrentHashMap<InComingEventKey, List<MessageContainer>>
                mInComingEvent = new ConcurrentHashMap<InComingEventKey, List<MessageContainer>>();
    
    private ConcurrentHashMap<FlowExecutor, InComingEventKey>
                mWaitingFlowToEvent = new ConcurrentHashMap<FlowExecutor, InComingEventKey>();

    private ConcurrentHashMap<Object, DefinitionMonitor>
                mDefToMonitor = new ConcurrentHashMap<Object, DefinitionMonitor>();

    /**
     * Deployed Blite program definitions
     */
    private Map<Object, BliteDeploymentDefinition> mProcessDefs = new Hashtable<Object, BliteDeploymentDefinition>();
    // The list with the deployed definition in a sensible order
    private List<BliteDeploymentDefinition> definitionsList = new ArrayList<BliteDeploymentDefinition>();
    
    
    /**
     * The Process Managers, one to one Blite Definition
     */
    private Map<BliteDeploymentDefinition, ProcessManager> mManagers = new Hashtable<BliteDeploymentDefinition, ProcessManager>();
    
    /**
     * The internal threadpool for executionFlow
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(INTERNAL_THREADPOOL_SIZE_DEFAULT);
    
    private Map<String, ProcessManager> mServiceNameToManagers = new HashMap<String, ProcessManager>();
    
    
    public EngineImp () {
    }

    public void deployProcessDefinition(BliteDeploymentDefinition bliteDef, String saName, String suName) {
        
        synchronized (mDeployLock) {
            // String id = bpelProcess.getBPELId();
            Object id = bliteDef.getBliteId();
            if (mProcessDefs.get(id) != null) {
                throw new RuntimeException("Business processes with duplicate id are " +
                        "not allowed in an engine. Business Process " + id.toString() + " id already registered");
            }

            DefinitionMonitor monitor = mDefToMonitor.get(id);
            ProcessManager processManager = new ProcessManagerImp(bliteDef, this, saName, suName, monitor);
            
            Set<String> names = new HashSet<String>();
            for (String serviceName : bliteDef.getServiceElement().provideAllServiceName()) {
                
                ProcessManager opm = mServiceNameToManagers.get(serviceName);
                if (opm != null 
//                        && opm != processManager
                    )
                    throw new IllegalStateException("Duplicated service name on different deployments: " + serviceName);
                
                //mServiceNameToManagers.put(serviceName, processManager);
                names.add(serviceName);
            }
            
            //we have passed all the checks we can start mapping the new definition
            mProcessDefs.put(id, bliteDef);
            Object retObj = mManagers.put(bliteDef, processManager);
            
            definitionsList.add(bliteDef);
            Collections.sort(definitionsList, new BliteDeploymentDefinition.DefComparator());
            
            for (String serviceName : names) {
                mServiceNameToManagers.put(serviceName, processManager);
            }
            
            mNewAddedDep = true;
        }

    }

    public void removeProcessDefinition(Object id) {
        if (mProcessDefs.get(id) == null) {
            throw new RuntimeException("Trying to unregister a process that has never been registered");
        }

        BliteDeploymentDefinition proc = mProcessDefs.remove(id);
        mManagers.remove(proc);
        definitionsList.remove(proc);
        
        for (String serviceName : proc.getServiceElement().provideAllServiceName()) {
            mServiceNameToManagers.remove(serviceName);
        }
    }

//    public void addDeploymentDefinition(BltDefDeployment deployment) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    public void queueFlowExecutor(FlowExecutor executor) {
        
        executorService.execute(new FlowExecutorRunnable(executor));
        
    }

    public EngineChannel getChannel() {
        return mChannel;
    }

    public void setChannel(EngineChannel channel) {
        this.mChannel = channel;
    }

    public void setMonitor(DefinitionMonitor monitor) {
        mDefToMonitor.put(monitor.getDefinition().getBliteId(), monitor);
    }
    
    
    /**
     * Internal class to map the Java 5 concurrency tecnology to
     * Blite Execution Model
     */
    private class FlowExecutorRunnable implements Runnable {

        private FlowExecutor executor;

        public FlowExecutorRunnable(FlowExecutor executor) {
            this.executor = executor;
        }
        
        public void run() {
            executor.executeCurrentActivity();
        }
    }
    
    /**
     * Add FlowExecutor to waithing the Incoming Event refered by the passed key
     * 
     * @param executor
     * @param eventKey
     */
    //synchronized 
    public void addFlowWaitingEvent(FlowExecutor executor, InComingEventKey eventKey) {
        
        InComingEventKey _pre = mWaitingFlowToEvent.get(executor);
        if (_pre != null) 
            throw new IllegalStateException("Flow " + executor + " is still waiting on event " + _pre);
        
        mWaitingFlowToEvent.put(executor, eventKey);
        
        List<FlowExecutor> l = mEventWaitingExecutor.get(eventKey);
        if (l == null) {
            l = new LinkedList<FlowExecutor>();
            mEventWaitingExecutor.put(eventKey, l);
        }
        
        l.add(executor);
        LOGGER.log(Level.INFO, "WAITED Flow on event " + eventKey.hashCode());
        
    }

    public List<FlowExecutor> resumeFlowWaitingEvent(InComingEventKey eventKey) {
        
        LOGGER.log(Level.INFO, "LOOKING for Flow on event " + eventKey.hashCode());
        
        List<FlowExecutor> l = mEventWaitingExecutor.remove(eventKey);
        
        if (l != null) {
            
            for (FlowExecutor flowExecutor : l) {
                
                mWaitingFlowToEvent.remove(flowExecutor);
                queueFlowExecutor(flowExecutor);
                
                LOGGER.log(Level.INFO, "RESUMED Flow on event " + eventKey.hashCode());
            }
            
        } else {
            l = new ArrayList<FlowExecutor>();
            LOGGER.log(Level.INFO, "NOT FOUND Flow on event " + eventKey.hashCode());
        }
        
        return l;
    }

    public void resumeWaitingFlow(FlowExecutor flow) {
        InComingEventKey icek = mWaitingFlowToEvent.remove(flow);
        
        if (icek != null) {
            List<FlowExecutor> l = mEventWaitingExecutor.get(icek);
            
            if (l != null) {
                l.remove(flow);
            }
            
            queueFlowExecutor(flow);
        }
    }
    
    

    public void addEventSubjet(InComingEventKey eventKey, MessageContainer mc) {
        List<MessageContainer> l = mInComingEvent.get(eventKey);
        if (l == null) {
            l = new LinkedList<MessageContainer>();
            mInComingEvent.put(eventKey, l);
            
            LOGGER.log(Level.INFO, "ADDED Event Subject " + mc + " for key " + eventKey.hashCode());
        }
        l.add(mc);
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Engine Comunication Interface
    
    //--------------------------------------------------------------------------
    //                 From Eviroment to Processes
    
    /**
     * Process the incoming request rooting it to the corret Process Manager
     * 
     * @param serviceId
     * @param operation
     * @param messageContainer
     */
    public void processRequest(ServiceIdentifier serviceId, String operation, MessageContainer messageContainer) {
        
        String serviceName = serviceId.provideStringServiceName();
        Object messageExchangeId = messageContainer.getId();
        
        ProcessManager m = mServiceNameToManagers.get(serviceName);
        mMExchangeToProcessManager.put(messageExchangeId, m);
        
        if (m == null) {
            //we report error to the requester
            Object exchangeId = messageContainer.getId();
            MessageContainer errorStatusMC = MessageContainerFactory.createMessageContainerForInvalidDestinatioPort(serviceId, exchangeId);
            getChannel().sendIntoExchange(exchangeId, errorStatusMC);
        } else {
            //we delyvery the request to the ProcessManager
            m.manageRequest(serviceId, operation, messageContainer);
        }
        
    }

    public void processExchange(MessageContainer messageContainer) {
        
        MessageContainer.Type mtype = messageContainer.getType();
        Object messageExchangeId = messageContainer.getId();
        
        
        if (mtype == MessageContainer.Type.STATUS_DONE) {
            ProcessManager targetProcess = mMExchangeToProcessManager.remove(messageExchangeId);
            InComingEventKey icek = InComingEventKeyFactory.createDoneStatusInComingEventKey(messageExchangeId);
            
            LOGGER.log(Level.INFO, "Waiting STATUS_DONE Event ...");
            
            synchronized (targetProcess.getDefinitionProcessLevelLock()) {
                LOGGER.log(Level.INFO, "Adding STATUS_DONE Event ...");
                
                addEventSubjet(icek, messageContainer);
                resumeFlowWaitingEvent(icek);
                
            }
            
        } else {

            throw new RuntimeException("Not yet supported");
        }
        
    }
    
    public Object startReadyToRunDefinition(Object deployId) {
        BliteDeploymentDefinition def = mProcessDefs.get(deployId);
        if (def != null) {
            ProcessManager manager = mManagers.get(def);
            if (manager != null)
                return manager.startReadyToRunDefinition();
        }
        return null;
    }

    public void startAllReadyToRunDefinitions() {
        for (ProcessManager processManager : mManagers.values()) {
            processManager.startReadyToRunDefinition();
        }
    }
    
    
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    //From Processes to Envirment
    
    public MessageContainer cosumeEvent(InComingEventKey inComingEventKey) {
        
        if (inComingEventKey instanceof DoneStatusInComingEventKey) {
            //in this case we have also to  free the pending resource
            
            DoneStatusInComingEventKey doneStatusInComingEventKey = (DoneStatusInComingEventKey) inComingEventKey;
            
            List<MessageContainer> l = mInComingEvent.remove(doneStatusInComingEventKey);
            if (l == null) return null;
             
            if (l.size() > 1) throw new IllegalStateException("Multiple Event was asked as a single event");
            
            
            MessageContainer mc = l.get(0);
            mChannel.closeExchange(mc.getId());
            
            return mc;
        }
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void consumeEvent(InComingEventKey inComingEventKey, MessageContainer messageContainer) {
        List<MessageContainer> l = provideEvents(inComingEventKey);
        
        for (MessageContainer mc : l) {
            if (mc.equals(messageContainer)) {
                l.remove(mc);
                //if we have no more event subjetc we clean the stuff...
                if (l.size() == 0) mInComingEvent.remove(inComingEventKey);
            }
        }
    }

    public List<MessageContainer> provideEvents(InComingEventKey inComingEventKey) {
        List<MessageContainer> l = mInComingEvent.get(inComingEventKey);
        
        if (l == null) l = new ArrayList<MessageContainer>();
        
        return l;
    }

    
    public InComingEventKey invoke(ServiceIdentifier serviceId, String operation, MessageContainer messageContainer, ProcessInstance instance) {
        Object meId = mChannel.createExchange(serviceId, operation, instance);
        messageContainer.setId(meId);
        
        mMExchangeToProcessManager.put(meId, instance.getManager());
        
        InComingEventKey icek = InComingEventKeyFactory.createDoneStatusInComingEventKey(meId);
        
        mChannel.sendIntoExchange(meId, messageContainer);
        
        return icek;
    }

    public void sendResponseDoneStatus(InComingEventKey inComingEventKey, Object meId) {
        
        MessageContainer messageContainer = MessageContainerFactory.createMessageContainerForDoneStatus(meId);
        
        mChannel.sendIntoExchange(meId, messageContainer);
        
    }

    public void sendResponseErrorStatus(String message, Object meId) {
        
        MessageContent content = MessageContentFactory.createStringMC(message);
        
        MessageContainer mc = 
                MessageContainerFactory.createMessageContainer(content, MessageContainer.Type.STATUS_ERROR);
        
        mChannel.sendIntoExchange(meId, mc);
    }

    public BliteDeploymentDefinition provideDefinition(Object deployId) {
        return mProcessDefs.get(deployId);
    }

    public List<BliteDeploymentDefinition> provideDefinitions() {
        return definitionsList;
    }

    //--------------------------------------------------------------------------

}

