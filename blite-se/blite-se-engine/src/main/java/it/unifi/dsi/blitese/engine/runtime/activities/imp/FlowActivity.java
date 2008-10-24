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

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.FlowOwner;
import it.unifi.dsi.blitese.engine.runtime.imp.FlowExecutorImp;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class FlowActivity extends ActivityComponentBase implements FlowOwner {
    
    static Logger LOGGER = Logger.getLogger(FlowActivity.class.getName()); 
    
    private int childFlowNumber;
    private int joinedChildFlow;
    private Engine engine;

    @Override
    public void init() {
        super.init();
        engine =  getContext().getProcessInstance().getManager().getEngine();
    }
    
    public boolean doActivity() {
        
        LOGGER.info("Staring Flow Activity");
        
        if (getContext().isInAFaultedBranch()) {
            LOGGER.info("Terminated activity " + this);
            flowParent();
            return true;
        }
        
        while (hasNextChildActivity()) {
            //we start our parallel child flow... I'm their owner and
            //they have my same context
            childFlowNumber++;
            
            FlowExecutor flowExecutor = new FlowExecutorImp(this);
            getContext().registerFlow(flowExecutor);
            ActivityComponent flowActivity = nextChildActivity(getContext(), flowExecutor);

            flowExecutor.setCurrentActivity(flowActivity);
            engine.queueFlowExecutor(flowExecutor);
            
            LOGGER.info("" + childFlowNumber + ") Child Flow Started" );
        }
        
        //we stop the current flow
        return false;
    }

    public void flowCompleted() {
        joinedChildFlow++;
        
        if (joinedChildFlow == childFlowNumber) {
            //all the childs flow have joined me so I can restart my 
            //original flow.
            LOGGER.info("All child flows have joined Resuming Original Flow");
            flowParent();
            engine.queueFlowExecutor(getExecutor());
        }
        
        //not all my childs have yet joined
        //I still have to wait 
    }

}
