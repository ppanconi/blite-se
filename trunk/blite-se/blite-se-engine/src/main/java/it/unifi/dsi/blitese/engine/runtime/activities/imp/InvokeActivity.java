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

import it.unifi.dsi.blitese.engine.runtime.InComingEventKey;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;
import it.unifi.dsi.blitese.engine.runtime.imp.MessageContainerFactory;
import it.unifi.dsi.blitese.engine.runtime.imp.MessageFactory;
import it.unifi.dsi.blitese.parser.BLTDEFInvokeActivity;

/**
 *
 * @author panks
 */
public class InvokeActivity extends ActivityComponentBase {
    
    private BLTDEFInvokeActivity activityDef;

    private InComingEventKey eventKey = null;
    private ProcessManager myManager;
    
    @Override
    public void init() {
        super.init();
        
        activityDef = (BLTDEFInvokeActivity) getBltDefNode();
        myManager = getContext().getProcessInstance().getManager();
    }
    
    

    public boolean doActivity() {

        if (eventKey == null) {
            return invoke();
        } else {
            MessageContainer resp = myManager.cosumeEvent(eventKey);
            return processStatus(resp);
        }
        
    }
    
    /**
     * Here the real invoke operation.
     * 
     * @return boolean
     *         true: the operation totaly completed. also the ack dono was recived.
     *         false: the ack done was not yet recived we need to wait for it.          
     */
    private boolean invoke() {
        
        String operation = activityDef.getOperationId().getName();
        
        //we obtain the runtime message for this invoke
        Object m = MessageFactory.createInvokeMessage(getContext(), activityDef);
        
        //we create the message container
        MessageContainer mc = MessageContainerFactory.createMessageContainer(m, null);

        ServiceIdentifier service = myManager.resovleParterLink(activityDef.getPartners(), getContext());
        
        //execute the invoke using the manager Facade interface.
        eventKey =  myManager.invoke(service, operation, mc, getContext().getProcessInstance());
        
        System.out.println("INVOKE on " + service + " operation " + operation + " passing " + m);
        
        synchronized (myManager.getDefinitionProcessLevelLock()) {
            
            MessageContainer resp = myManager.cosumeEvent(eventKey);
            
            if (resp != null) {
                return processStatus(resp);
            } else {
                //we start to waiting
                myManager.getEngine().addFlowWaitingEvent(getExecutor(), eventKey);
                return false;
            }
            
        }
    }

    private boolean processStatus(MessageContainer mc) {
        getExecutor().setCurrentActivity(getParentComponent());
        return true;
    }
}
