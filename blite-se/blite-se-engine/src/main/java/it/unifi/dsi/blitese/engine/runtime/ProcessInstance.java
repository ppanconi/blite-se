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
import it.unifi.dsi.blitese.parser.BLTDEFReceiveActivity;


public interface ProcessInstance extends FlowOwner, ActivityComponent, ExecutionContext {

    void activete();
    
    BliteDeploymentDefinition getDeploymentDefinition();
    
    ProcessManager getManager();
    
    String getInstanceId();

    void setMonitor(InstanceMonitor monitor);

    InstanceMonitor getMonitor();

    public void createRecActivated(BLTDEFReceiveActivity rec);
}

