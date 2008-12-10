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

import it.unifi.dsi.blitese.parser.BLTDEFInvPartners;
import java.util.List;


public interface ProcessManager {
    
    public Engine getEngine();

    /**
     * The monitor for the menaged definition
     * @return DefinitionMonitor
     */
    public DefinitionMonitor getMonitor();

    public void setMonitor(DefinitionMonitor monitor);

    /**
     * Execute the invoke operation on the provided Partner Link 
     * with the provided Operation and passing the provided Message Container. 
     * This mathod is called by the instance to invoke the remote provider.
     * 
     * @param runtimePartnerLink the key object to locate the target partner link,
     *        TODO specialize this type       
     * @param operation
     * @param messageContainer
     * @param instance 
     * 
     * @return InComingEventKey. The evet key for the next step comuniaction protocol.
     *         Actual only one-way invoke are present so the the next event will be only
     *         an StatusDone or StatusError
     */
    public InComingEventKey invoke(ServiceIdentifier serviceId, String operation, 
            MessageContainer messageContainer, ProcessInstance instance);
    
    
    /**
     * Resolve the PartnerLink at rintime.
     * 
     * @param partnersmDef Static definition of the PartnerLink
     * @param variableScope Runtime variable scope
     * 
     * @return Object the runtime partner link.
     */
    public ServiceIdentifier resovleParterLink(BLTDEFInvPartners partnersDef, VariableScope variableScope);
    
    /**
     * 
     * @param inComingEventKey
     * @return
     */
    MessageContainer cosumeEvent(InComingEventKey inComingEventKey);
    
    /**
     * Consume the multiple event/key.
     *  
     * @param inComingEventKey
     * @param messageContainer
     */
//    void consumeEvent(InComingEventKey inComingEventKey, MessageContainer messageContainer);
    
    /**
     * Return the list all Event related to the provieded key
     * @param inComingEventKey
     * @return
     */
    List<MessageContainer> provideEvents(InComingEventKey inComingEventKey);
    
    
    /**
     * Provide a lock at Process definition Level
     * 
     * @return Object the object used to get a lock at process definition level.
     */
    public Object getDefinitionProcessLevelLock();
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Engine to Process notification
    
    public void manageRequest(ServiceIdentifier serviceId, String operation, 
                              MessageContainer messageContainer);
    
    /**
     * Start the readyToRun definition. Thi invocation have a real effect 
     * only if the precessManager definition is oof type ReadyToRun
     * 
     * @return Objetc the Instace id for the startd definition
     */
    public Object startReadyToRunDefinition();
}

