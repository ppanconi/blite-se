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
        System.out.println("INVOKE on " + activityDef.getPartners() + " operation " + activityDef.getOperationId() + " passing " + activityDef.getParams());
        getExecutor().setCurrentActivity(getParentComponent());
        return true;
    }

}
