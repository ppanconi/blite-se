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

package it.unifi.dsi.blitese.localenv.gui.env.nodes;

import it.unifi.dsi.blitese.localenv.gui.env.EnvModel;
import it.unifi.dsi.blitese.localenv.gui.nodes.BaseNode;

/**
 *
 * @author panks
 */
public abstract  class EnvBaseNode extends BaseNode {
    
    private EnvModel envModel;

    public EnvBaseNode(String title, String iconResName, EnvModel envModel) {
        super(title, iconResName);
        this.envModel = envModel;
    }

    public EnvBaseNode(String title, String iconResName) {
        super(title, iconResName);
    }

    public EnvBaseNode() {
    }

    public EnvModel getEnvModel() {
        return envModel;
    }

    public void setEnvModel(EnvModel envModel) {
        this.envModel = envModel;
    }
    
    

}
