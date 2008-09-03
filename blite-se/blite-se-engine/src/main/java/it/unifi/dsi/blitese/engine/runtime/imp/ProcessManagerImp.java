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
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.ProcessManager; 
import it.unifi.dsi.blitese.engine.runtime.ResponseInComingEventKey;
import it.unifi.dsi.blitese.engine.runtime.VariableScope;
import it.unifi.dsi.blitese.parser.ABltValueHolder;
import it.unifi.dsi.blitese.parser.BLTDEFInvPartners;
import it.unifi.dsi.blitese.parser.BLTDEFServiceInstance;
import java.util.Map;

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
    
    private Object processLevelLock = new Object();

    public ProcessManagerImp(BliteDeploymentDefinition bliteProcessDef, Engine engine, String saName, String suName) {
        mBliteProcessDef = bliteProcessDef;
        mEngine = engine;
        
        mSaName = (saName != null) ? saName : "unavailable";
	mSuName = (suName != null) ? suName : "unavailable";

        //if the static definition conteins same ready to run instances
        //we start with them
        for (BLTDEFServiceInstance instDef : bliteProcessDef.getDefProcessInstances()) {
            
            ProcessInstance processInstance = 
                    new ProcessInstanceImp(mEngine, this, null, bliteProcessDef);
            processInstance.activete(instDef);
            
        }
        
    }

    public Object invoke(Object runtimePartnerLink, String operation, MessageContainer messageContainer, ProcessInstance instance) {
        return mEngine.getChannel()
                    .invoke(runtimePartnerLink, operation, messageContainer, instance);
    }

    public Object resovleParterLink(BLTDEFInvPartners partnersDef, VariableScope variableScope) {
        
        ABltValueHolder vh = partnersDef.getOther();
        return RuntimeValueFactory.makeRuntimeValue(vh, variableScope);
    }

    public Object getProcessLevelLock() {
        return processLevelLock;
    }

    public Map<ResponseInComingEventKey, MessageContainer> getEventDoneMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
    
}

