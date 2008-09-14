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
public abstract class AServiceElement extends SimpleNode {

    private List<BLTDEFReceiveActivity> ports = new ArrayList<BLTDEFReceiveActivity>();

    public AServiceElement(BliteParser p, int i) {
        super(p, i);
    }

    public AServiceElement(int i) {
        super(i);
    }

    public SimpleNode getUniqueChild() {
        return (SimpleNode) jjtGetChild(0);
    }
    
    public void addPort(BLTDEFReceiveActivity port) {
        ports.add(port);
    }
}
