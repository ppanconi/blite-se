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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class SequenceActivity extends ActivityComponentBase {
    
    static Logger LOGGER = Logger.getLogger(SequenceActivity.class.getName());
    
    private ActivityComponent lastChildAct = null;

    public boolean doActivity() {
        
        if (getContext().isInAFaultedBranch()) {
            LOGGER.info("Terminated activity " + this);
            flowParent();
            return true;
        }
        
        ActivityComponent _nextAct = nextChildActivity(getContext(), getExecutor());
        
        LOGGER.log(Level.FINE, "SequenceActivity doActivity");
        
        if (_nextAct == null) {
            //put the parent on the flow
            LOGGER.log(Level.FINE, "SequenceActivity FINISHED");
            flowParent();
        } else {
            //put it on the flow
            LOGGER.log(Level.FINE, "SequenceActivity flow the Child " + currentChildIndex + ") " + _nextAct);
            getExecutor().setCurrentActivity(_nextAct);
        }
        
        lastChildAct = _nextAct;
        return true;
    }

}
