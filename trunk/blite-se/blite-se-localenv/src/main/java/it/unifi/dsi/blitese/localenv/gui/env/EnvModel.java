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

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.localenv.EngineLocation;
import it.unifi.dsi.blitese.localenv.IncompatibleCompUnitException;
import it.unifi.dsi.blitese.localenv.LocalEnvironment;
import it.unifi.dsi.blitese.localenv.gui.env.nodes.DefinitionNode;
import it.unifi.dsi.blitese.localenv.gui.env.nodes.EngineNode;
import it.unifi.dsi.blitese.localenv.gui.env.nodes.RootNode;
import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author panks
 */
public class EnvModel implements TreeModel {
    
    private LocalEnvironment env;
    private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
    private RootNode rootNode = new RootNode(this);

    public EnvModel() {
        this.env = new LocalEnvironment();
    }
    
//    public EnvModel(LocalEnvironment env) {
//        this.env = env;
//    }
    
    
    public LocalEnvironment getEnv() {
        return env;
    }

    public void setEnv(LocalEnvironment env) {
        this.env = env;
    }

    public Object getRoot() {
        return rootNode;
    }

    public Object getChild(Object parent, int index) {
        
        if (parent instanceof RootNode) {
            
            EngineLocation loc = env.provideSortLocations().get(index);
            Engine engine = env.provideEngineAt(loc);
            
            return new EngineNode(loc, this);
            
        } else if (parent instanceof EngineNode){
            
            EngineNode en = (EngineNode) parent;
            Engine engine = env.provideEngineAt(en.getEngineLocation());
            
            BliteDeploymentDefinition def = engine.provideDefinitions().get(index);
            
            return new DefinitionNode(this, def);
             
        }
        return null;
    }

    public int getChildCount(Object parent) {
    
        if (parent instanceof RootNode) {
            
            return env.provideSortLocations().size();
            
        } else if (parent instanceof EngineNode){
            
            EngineNode en = (EngineNode) parent;
            Engine engine = env.provideEngineAt(en.getEngineLocation());
            
            return engine.provideDefinitions().size();
        }
//        throw new UnsupportedOperationException("Not supported yet.");
        return 0;
    }

    public boolean isLeaf(Object node) {
        if (node instanceof DefinitionNode) {
            return true;
        } else return false;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof RootNode) {
            
            return env.provideSortLocations().indexOf(child);
            
        } else if (parent instanceof EngineNode){
            
            EngineNode en = (EngineNode) parent;
            Engine engine = env.provideEngineAt(en.getEngineLocation());
            
            return engine.provideDefinitions().indexOf(child);
        }
        return -1;
    }

    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

    public void addCUtoEnviroment(BLTDEFCompilationUnit compilationUnit) throws IncompatibleCompUnitException{
        env.addCompilationUnit(compilationUnit);
        TreeModelEvent event = new TreeModelEvent(this, new Object[] {rootNode});
        
        for (TreeModelListener l : listeners) {
            l.treeStructureChanged(event);
        }
    }
    
}

