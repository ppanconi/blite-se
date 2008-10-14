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

package it.unifi.dsi.blitese.parser;

/**
 *
 * @author panks
 */
public abstract class AScope extends SimpleNode {

    public AScope(BliteParser p, int i) {
        super(p, i);
    }

    public AScope(int i) {
        super(i);
    }

//added code start here ////////////////////////////////////////////////////////  
    
    private BLTDEFActivity mainActivity;
    private BLTDEFActivity faultHandler;
    private BLTDEFActivity compensationHandler;

    public BLTDEFActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(BLTDEFActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public BLTDEFActivity getCompensationHandler() {
        return compensationHandler;
    }

    public void setCompensationHandler(BLTDEFActivity compensationHandler) {
        this.compensationHandler = compensationHandler;
    }

    public BLTDEFActivity getFaultHandler() {
        return faultHandler;
    }

    public void setFaultHandler(BLTDEFActivity faultHandler) {
        this.faultHandler = faultHandler;
    }
    
    
}
