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

/**
 * This class represents an activity execution context.
 *
 * This could be the Process Instance itself but also other
 * structured activities like scope activity.
 * 
 * At run time Execution Contexts are in tree 
 * 
 * 
 * @author panks
 */
public interface ExecutionContext extends VariableScope {
    
    ProcessInstance getProcessInstance();
    
    boolean matchCorrelation(String variable, Object value);

    ExecutionContext getParent();
    
    void notifyFault(Fault fault);
    
    void registerFlow(FlowExecutor flow);
    
    void registerInnerContext(ExecutionContext child);
    
    void resumeWaithingFlows();
    
}
