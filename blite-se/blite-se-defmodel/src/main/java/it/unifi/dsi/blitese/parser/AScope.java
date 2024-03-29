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
    
    private BltDefBaseNode mainActivity;
    //The Dafult fault Handler reThrow the exception
    private BltDefBaseNode faultHandler = new BLTDEFThrowActivity();
    private BltDefBaseNode compensationHandler;

    public BltDefBaseNode getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(BltDefBaseNode mainActivity) {
        this.mainActivity = mainActivity;
    }

    public BltDefBaseNode getCompensationHandler() {
        return compensationHandler;
    }

    public void setCompensationHandler(BltDefBaseNode compensationHandler) {
        this.compensationHandler = compensationHandler;
    }

    public BltDefBaseNode getFaultHandler() {
        return faultHandler;
    }

    public void setFaultHandler(BltDefBaseNode faultHandler) {
        if (faultHandler != null)
            this.faultHandler = faultHandler;
    }
    
    
}
