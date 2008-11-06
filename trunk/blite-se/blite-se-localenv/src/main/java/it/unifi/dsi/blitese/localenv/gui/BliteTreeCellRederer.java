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

package it.unifi.dsi.blitese.localenv.gui;

import org.jdesktop.application.ResourceMap;

import it.unifi.dsi.blitese.localenv.gui.nodes.BaseNode;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.jdesktop.application.View;

public class BliteTreeCellRederer extends DefaultTreeCellRenderer {

        static ResourceMap resourceMap = DesktopApplication.getInstance().getContext().getResourceMap(DesktopView.class, View.class);
    
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
           
            BaseNode bn = null;
            if (value instanceof BaseNode) {
                bn = (BaseNode) value;
            } else if (value instanceof DefaultMutableTreeNode) {
            
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                bn = (BaseNode) node.getUserObject();
            } else
                throw new RuntimeException("Not Allowed value for node: " + value + " of class " + value.getClass());
            
            if (bn.getIconResName() != null)
                setIcon(resourceMap.getIcon(bn.getIconResName()));
            if (bn.getTitle() != null)
                setText(bn.getTitle());
            
            return this;
        }
        
        
        
    }