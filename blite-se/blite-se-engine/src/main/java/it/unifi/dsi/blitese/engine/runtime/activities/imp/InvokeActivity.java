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
import it.unifi.dsi.blitese.engine.runtime.imp.MessageContainerFactory;
import it.unifi.dsi.blitese.engine.runtime.imp.MessageFactory;
import it.unifi.dsi.blitese.parser.BLTDEFInvokeActivity;

/**
 *
 * @author panks
 */
public class InvokeActivity extends ActivityComponentBase {
    
    private BLTDEFInvokeActivity activityDef;

    @Override
    public void init() {
        super.init();
        
        activityDef = (BLTDEFInvokeActivity) getBltDefNode();
    }
    
    

    public boolean doActivity() {

        invoke();
        getExecutor().setCurrentActivity(getParentComponent());
        
        return true;
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

        //menage the correlation here
        //TODO
        
        ProcessManager myManager = getContext().getProcessInstance().getManager();
        
        Object rtPartnerLink = myManager.resovleParterLink(activityDef.getPartners(), getContext());
        
        //execute the invoke using the manager Facade interface.
        Object messageExchangeId = myManager.invoke(rtPartnerLink, operation, mc, getContext().getProcessInstance());
        
        Object lock = myManager.getProcessLevelLock();
        
        //we have to check if we have the ack
        synchronized (lock) {
            
            
            
        }
        
        System.out.println("INVOKE on " + rtPartnerLink + " operation " + operation + " passing " + m);
        return false;
    }

}
