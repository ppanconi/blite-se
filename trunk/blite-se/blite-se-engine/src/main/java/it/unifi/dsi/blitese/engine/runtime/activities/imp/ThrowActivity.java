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

import it.unifi.dsi.blitese.engine.runtime.Fault;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class ThrowActivity extends ActivityComponentBase {

    static Logger LOGGER = Logger.getLogger(ThrowActivity.class.getName()); 
    
    public boolean doActivity() {
        LOGGER.info("THROW A FAULT");
        getContext().notifyFault(new Fault() {});
        flowParent();
        return true;
    }
    
    

}
