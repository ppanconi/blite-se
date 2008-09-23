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

package it.unifi.dsi.blitese.engine.runtime.activities.imp;

import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import it.unifi.dsi.blitese.engine.runtime.RuntimeVariable;
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;
import it.unifi.dsi.blitese.engine.runtime.imp.InComingEventKeyFactory;
import it.unifi.dsi.blitese.engine.runtime.imp.RequestInComingEventKey;
import it.unifi.dsi.blitese.engine.runtime.imp.RuntimeValueFactory;
import it.unifi.dsi.blitese.parser.BLTDEFBoundId;
import it.unifi.dsi.blitese.parser.BLTDEFReceiveActivity;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class ReceiveActivity extends ActivityComponentBase {

    static Logger log = Logger.getLogger(ReceiveActivity.class.getName());
    
    private BLTDEFReceiveActivity def;
    private ProcessManager manager;
    private RequestInComingEventKey icek;
    private String portId;

    private String[] fparamNames;
    
    @Override
    public void init() {
        super.init();
        def = (BLTDEFReceiveActivity) getBltDefNode();
        manager = getContext().getProcessInstance().getManager();
        
        String serviceName = def.getPartners().getServiceName();
        ServiceIdentifier sid = new ServiceIdentifier(null, serviceName);
        portId = def.getPortId();
        
        icek = InComingEventKeyFactory.createRequestInComingEventKey(sid, portId);
        
        List<BLTDEFBoundId> l = def.getParams().getFormalParams();
        fparamNames = new String[l.size()];
        
        int i = 0;
        for (BLTDEFBoundId var : l) {
            fparamNames[i++] = var.getName(); 
        }
    }
    
    

    public boolean doActivity() {
        
        log.info(" Try to revice on port " + portId);
        
        MessageContainer matchingMessage = null;
        synchronized (manager.getDefinitionProcessLevelLock()) {
            
            List<MessageContainer> mcs = manager.provideEvents(icek);
            
            for (MessageContainer mc : mcs) {
                boolean toDiscard = false;
                
                Object[] parts = mc.getContent().getParts();

                for (int i = 0; i < parts.length; i++) {
                    Object part = parts[i];
                    String var = fparamNames[i];

                    if (! getContext().matchCorrelation(var, part)) {
                        //this is not a message of mine...
                        toDiscard = true;
                        break; //try the next one...
                    }
                }
                
                if (! toDiscard) {
                    matchingMessage = mc;
                    break; //we have found one message we stop searching...
                }
            }
            
            if (matchingMessage != null) {
                //we have found samething than we consume it...
                manager.consumeEvent(icek, matchingMessage);
                
            } else {
                //we haven't found anything we continue to wait...
                manager.getEngine().addFlowWaitingEvent(getExecutor(), icek);
                return false;
            }
        }
        
        assert matchingMessage != null;
        
        log.info(" Recived Message " + matchingMessage);
            
        //we assign the incaming values into the contextual variable scope;
        Object[] parts = matchingMessage.getContent().getParts();
        
        for (int i = 0; i < fparamNames.length; i++) {
            String varName = fparamNames[i];
            Object value = parts[i];
            
            RuntimeVariable rv = RuntimeValueFactory.makeRuntimeValue(varName, value);
            getContext().setRuntimeVariable(rv);
            
        }
        
        return true;
        
    }

}
