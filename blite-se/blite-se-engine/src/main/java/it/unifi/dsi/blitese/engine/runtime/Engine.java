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
package it.unifi.dsi.blitese.engine.runtime;

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import java.util.List;


/**
 *
 * @author panks
 */
public interface Engine {
    
    public static final int INTERNAL_THREADPOOL_SIZE_DEFAULT = 10;
    
    /**
     * Add a Blite Process Definition to the Engine.
     * @param def
     * @param saName
     * @param suName
     */
    void deployProcessDefinition(BliteDeploymentDefinition def, String saName, String suName);
    
    /**
     * Undeply a Blite Process Definition from the Engine.
     * @param id the Process Definition Unique id.
     */
    void removeProcessDefinition(Object id);
    
    EngineChannel getChannel();
    
    void setChannel(EngineChannel channel);
    
//    /**
//     * Add a Blite DefDeployment 
//     * @param deployment
//     */
//    void addDeploymentDefinition(BltDefDeployment deployment);
    
    
    /**
     * Add a ready to run executor to queue where the working threads get
     * the current works.
     * @param executorbig breast archive
     */
    void queueFlowExecutor(FlowExecutor executor);
    
    /**
     * Put the executor in the waiting queue for the incoming event.
     * 
     * @param executor
     * @param eventKey
     */
    void addFlowWaitingEvent(FlowExecutor executor, InComingEventKey eventKey);
    
    /**
     * Resume the executors waitng for the specified event key
     * @param eventKey
     */
    public List<FlowExecutor> resumeFlowWaitingEvent(InComingEventKey eventKey);
    
    
    public void addEventSubjet(InComingEventKey eventKey, MessageContainer mc);
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Engine Comunication Interface
    
    //--------------------------------------------------------------------------
    //                 From Eviroment to Processes
    
    void processRequest(ServiceIdentifier serviceId, String operation, MessageContainer messageContainer);
    
    void processExchange(MessageContainer messageContainer);
    
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    //From Processes to Envirment
    
    InComingEventKey invoke(ServiceIdentifier serviceId, String operation, 
            MessageContainer messageContainer, ProcessInstance instance);

    /**
     * Try to consume the UNIQUE event with the specified key.
     * 
     * If with that key there are more than one Event then an exception is thrown.
     *
     * If no event content is present for this event null is returned.
     * 
     * @param inComingEventKey
     * @return The Event Subject or null
     */
    MessageContainer cosumeEvent(InComingEventKey inComingEventKey);
    
    
    /**
     * Consume the multiple event/key.
     *  
     * @param inComingEventKey
     * @param messageContainer
     */
    void consumeEvent(InComingEventKey inComingEventKey, MessageContainer messageContainer);
    
    /**
     * Return the list all Event related to the provieded key
     * @param inComingEventKey
     * @return
     */
    List<MessageContainer> provideEvents(InComingEventKey inComingEventKey);
    
    
    /**
     * Send done status for the response from the server for the invoke from
     * this SE as a client.
     * 
     * @param  inComingEventKey
     */
    public  void sendResponseDoneStatus(InComingEventKey inComingEventKey, Object meId);
    
    
    public  void sendResponseErrorStatus(String message, Object meId);

    //--------------------------------------------------------------------------
}

