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

package it.unifi.dsi.blitese.localenv.gui.env;

import it.unifi.dsi.blitese.localenv.LocalEnvironment;
import it.unifi.dsi.blitese.localenv.gui.env.nodes.RootNode;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author panks
 */
public class EnvModel implements TreeModel {
    
    private LocalEnvironment env = new LocalEnvironment();

    public LocalEnvironment getEnv() {
        return env;
    }

    public void setEnv(LocalEnvironment env) {
        this.env = env;
    }
    
    

    public Object getRoot() {
        return new RootNode(this);
    }

    public Object getChild(Object parent, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getChildCount(Object parent) {
        return 0;
    }

    public boolean isLeaf(Object node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getIndexOfChild(Object parent, Object child) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addTreeModelListener(TreeModelListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeTreeModelListener(TreeModelListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
