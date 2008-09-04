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
import it.unifi.dsi.blitese.engine.runtime.EngineChannel;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.InComingEventKey;
import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    
    private ConcurrentMap<InComingEventKey, FlowExecutor>
                mEventWaitingExecutor = new ConcurrentHashMap<InComingEventKey, FlowExecutor>();
    
    /**
     * Deployed Blite program definitions
     */
    private Map<Object, BliteDeploymentDefinition> mProcessDefs = new Hashtable<Object, BliteDeploymentDefinition>();
    
    /**
     * The Process Managers, one to one Blite Definition
     */
    private Map<BliteDeploymentDefinition, ProcessManager> mManagers = new Hashtable<BliteDeploymentDefinition, ProcessManager>();
    
    /**
     * The internal threadpool for executionFlow
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(INTERNAL_THREADPOOL_SIZE_DEFAULT);

    public EngineImp () {
    }

    public void addProcessDefinition(BliteDeploymentDefinition bliteDef, String saName, String suName) {
        
        synchronized (mDeployLock) {
            // String id = bpelProcess.getBPELId();
            Object id = bliteDef.getBliteId();
            if (mProcessDefs.get(id) != null) {
                throw new RuntimeException("Business processes with duplicate id are " +
                        "not allowed in an engine. Business Process " + id.toString() + " id already registered");
            }

            mProcessDefs.put(id, bliteDef);

            ProcessManager processManager = new ProcessManagerImp(bliteDef, this, saName, suName);
            Object retObj = mManagers.put(bliteDef, processManager);
            if (retObj != null) {
                throw new RuntimeException("Fatal Error: this process is already loaded process Id: " + bliteDef.getBliteId());
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
        mEventWaitingExecutor.put(eventKey, executor);
    }
           
    

}

