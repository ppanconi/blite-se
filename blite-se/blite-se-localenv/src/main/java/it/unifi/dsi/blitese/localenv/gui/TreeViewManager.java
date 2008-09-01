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

import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BLTDEFDeployment;
import it.unifi.dsi.blitese.parser.BLTDEFServiceDef;
import it.unifi.dsi.blitese.parser.BLTDEFServiceInstance;
import java.awt.Component;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.View;

/**
 *
 * @author panks
 */
public class TreeViewManager {
    
    private Map<URL, MutableTreeNode> mCompUnitToNodeIndex = new HashMap<URL, MutableTreeNode>();

    private JTree tree;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;

    public TreeViewManager(JTree tree) {
        this.tree = tree;

        DefaultMutableTreeNode _root = new DefaultMutableTreeNode("Env");
        root = _root;

        model = new DefaultTreeModel(root);
        tree.setModel(model);
        tree.setCellRenderer(new BliteTreeCellRederer());
    }
    
    public void synchCompilationUnit(BLTDEFCompilationUnit compilationUnit) {
        
        MutableTreeNode node = mCompUnitToNodeIndex.remove(compilationUnit.getResource());
        if (node != null) {
            model.removeNodeFromParent(node);
        }
        
        node = new DefaultMutableTreeNode(compilationUnit.getResource());
        
        
        model.insertNodeInto(node, root, root.getChildCount());
        mCompUnitToNodeIndex.put(compilationUnit.getResource(), node);
        
        int i = 0;
        for (BLTDEFDeployment dep : compilationUnit.getDeployments()) {
            DefaultMutableTreeNode depNode = new DefaultMutableTreeNode("Dep. " + (i + 1));
            model.insertNodeInto(depNode, node, i++);
            tree.scrollPathToVisible(new TreePath(depNode.getPath()));
            
            int j = 0;
            BLTDEFServiceDef serviceDefinition = dep.provideServiceDefinition();
            if (serviceDefinition != null) {
                DefaultMutableTreeNode defNode = new DefaultMutableTreeNode("Def. " + (j + 1));
                model.insertNodeInto(defNode, depNode, j++);
                tree.scrollPathToVisible(new TreePath(defNode.getPath()));
            }
            
            j = 0;
            for (BLTDEFServiceInstance serInst : dep.provideAllInsatnces()) {
                DefaultMutableTreeNode instNode = new DefaultMutableTreeNode("Inst. " + (j + 1));
                model.insertNodeInto(instNode, depNode, j++);
                tree.scrollPathToVisible(new TreePath(instNode.getPath()));
            }
        }        
        
    }
    
    static ResourceMap resourceMap =
        DesktopApplication.getInstance().getContext().getResourceMap(DesktopView.class, View.class);
    
    
    private class BliteTreeCellRederer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
           
//            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
//            node.get
//            
//            setIcon(resourceMap.getIcon("envTree.dep.icon"));
//            
            return this;
        }
        
        
        
    }
}
