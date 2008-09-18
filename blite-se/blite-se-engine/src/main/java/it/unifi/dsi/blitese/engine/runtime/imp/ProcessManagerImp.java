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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *
 * @author panks
 */

public class ProcessManagerImp implements ProcessManager {
    
    private BliteDeploymentDefinition mBliteProcessDef;
    private Engine mEngine;
    private String mSaName;
    private String mSuName;
    
    private Object definitionProcessLevelLock = new Object();
    private Map<String, ProcessInstance> mInstances = new HashMap<String, ProcessInstance>();
    private Map<String, BLTDEFReceiveActivity> mPortIdToPortDef = new HashMap<String, BLTDEFReceiveActivity>();
    
    

    public ProcessManagerImp(BliteDeploymentDefinition bliteProcessDef, Engine engine, String saName, String suName) {
        mBliteProcessDef = bliteProcessDef;
        mEngine = engine;
        
        mSaName = (saName != null) ? saName : "unavailable";
	mSuName = (suName != null) ? suName : "unavailable";

        
        
//        //if the static definition conteins same ready to run instance
//        //we start with it
        BLTDEFServiceInstance readyToRunInstance = bliteProcessDef.provideServiceInstance();
        
        if (readyToRunInstance != null) {
            ProcessInstance instance = createInstance();
            instance.activete();
        }
        
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

    ////////////////////////////////////////////////////////////////////////////
    // Utility 
    private Long instNumber;
    synchronized  private ProcessInstance createInstance() {
        ProcessInstance i = new ProcessInstanceImp(mEngine, this, "" + mBliteProcessDef.getBliteId() + instNumber++ , mBliteProcessDef);
        mInstances.put(i.getInstanceId(), i);
        return i;
    }

    public void manageRequest(String operation, MessageContainer messageContainer) {
        
        Logger.getLogger(ProcessManagerImp.class.toString()).log(Level.INFO, "Mmanaging Request ...");
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

