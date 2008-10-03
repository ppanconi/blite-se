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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author panks
 */
public class BLTDEFInvParams extends SimpleNode {

    public BLTDEFInvParams(BliteParser p, int i) {
        super(p, i);
    }

    public BLTDEFInvParams(int i) {
        super(i);
    }

    /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

//added code start here ////////////////////////////////////////////////////////
  
    private List<BLTDEFInvParam> actualParams = new ArrayList<BLTDEFInvParam>();

    public List<BLTDEFInvParam> getActualParams() {
        return actualParams;
    }

    public void setActualParams(List<BLTDEFInvParam> actualParams) {
        this.actualParams = actualParams;
    }
    
    public void addActualParam(BLTDEFInvParam param) {
        this.actualParams.add(param);
    }
}
