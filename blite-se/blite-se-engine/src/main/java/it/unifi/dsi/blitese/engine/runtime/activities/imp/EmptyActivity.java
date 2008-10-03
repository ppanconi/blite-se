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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class EmptyActivity extends ActivityComponentBase {
    
    private static final Logger LOGGER = Logger.getLogger(EmptyActivity.class.getName());

    public boolean doActivity() {
        
        LOGGER.log(Level.INFO, "EMPTYACTIVITY what's wonderful life... :)");
        
        flowParent();
        return true;
        
    }

}
