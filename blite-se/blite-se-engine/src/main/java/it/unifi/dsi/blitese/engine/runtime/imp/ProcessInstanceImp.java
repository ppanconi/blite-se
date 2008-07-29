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

import it.unifi.dsi.blitese.engine.definition.ActivityComponentFactory;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance; 

import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;
import it.unifi.dsi.blitese.parser.SimpleNode;

public class ProcessInstanceImp implements ProcessInstance {
    
    private Engine mEngine;
    private ProcessManager mProcessManager;
    private String instanceId; 
    private SimpleNode bliteDefinition;

    public ProcessInstanceImp(Engine mEngine, ProcessManager mProcessManager, 
                              String instanceId, SimpleNode bliteDefinition) {
        this.mEngine = mEngine;
        this.mProcessManager = mProcessManager;
        this.instanceId = instanceId;
        this.bliteDefinition = bliteDefinition;
       
    }
    
    public void activete() {
        
        FlowExecutor executor = new FlowExecutorImp(this);
        
        BltDefBaseNode myChilDDefNode = (BltDefBaseNode) bliteDefinition.jjtGetChild(0);
                
        ActivityComponent myChilsActivity = ActivityComponentFactory.getInstance()
                .makeRuntimeActivity(myChilDDefNode, this, this, executor);
        
        executor.setCurrentActivity(myChilsActivity);
        mEngine.queueFlowExecutor(executor);
        
    }

    public void flowCompleted() {
        
        System.out.println("Process Instance" + instanceId + " completed");
        
    }

    public boolean doActivity() {
        return false;
    }

    

}

