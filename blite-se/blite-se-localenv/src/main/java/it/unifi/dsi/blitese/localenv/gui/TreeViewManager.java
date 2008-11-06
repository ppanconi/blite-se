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

import it.unifi.dsi.blitese.localenv.gui.nodes.DefNode;
import it.unifi.dsi.blitese.localenv.gui.nodes.DepNode;
import it.unifi.dsi.blitese.localenv.gui.nodes.RootNode;
import it.unifi.dsi.blitese.localenv.gui.nodes.FileNode;
import it.unifi.dsi.blitese.localenv.gui.nodes.InstNode;
import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BLTDEFDeployment;
import it.unifi.dsi.blitese.parser.BLTDEFServiceDef;
import it.unifi.dsi.blitese.parser.BLTDEFServiceInstance;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

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

        DefaultMutableTreeNode _root = new DefaultMutableTreeNode(new RootNode("Files"));
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
        
        node = new DefaultMutableTreeNode(new FileNode(compilationUnit.getResource().toString()));
        
        
        model.insertNodeInto(node, root, root.getChildCount());
        mCompUnitToNodeIndex.put(compilationUnit.getResource(), node);
        
        int i = 0;
        for (BLTDEFDeployment dep : compilationUnit.getDeployments()) {
            DefaultMutableTreeNode depNode = new DefaultMutableTreeNode(new DepNode("Dep. " + (i + 1)));
            model.insertNodeInto(depNode, node, i++);
            tree.scrollPathToVisible(new TreePath(depNode.getPath()));
            
            int j = 0;
            BLTDEFServiceDef serviceDefinition = dep.provideServiceDefinition();
            if (serviceDefinition != null) {
                DefaultMutableTreeNode defNode = new DefaultMutableTreeNode(new DefNode("Def. " + (j + 1)));
                model.insertNodeInto(defNode, depNode, j++);
                tree.scrollPathToVisible(new TreePath(defNode.getPath()));
            }
            
            j = 0;
            for (BLTDEFServiceInstance serInst : dep.provideAllInsatnces()) {
                DefaultMutableTreeNode instNode = new DefaultMutableTreeNode(new InstNode("RtR Def." + (j + 1)));
                model.insertNodeInto(instNode, depNode, j++);
                tree.scrollPathToVisible(new TreePath(instNode.getPath()));
            }
        }        
        
    }
    
}
