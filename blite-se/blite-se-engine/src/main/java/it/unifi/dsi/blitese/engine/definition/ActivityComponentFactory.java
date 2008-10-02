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
package it.unifi.dsi.blitese.engine.definition;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.ExecutionContext;
import it.unifi.dsi.blitese.engine.runtime.FlowExecutor;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ActivityComponentBase;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.AssignActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.InvokeActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ReceiveActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.SequenceActivity;
import it.unifi.dsi.blitese.parser.ABTLDEFSequenceActivity;
import it.unifi.dsi.blitese.parser.BLTDEFAssignActivity;
import it.unifi.dsi.blitese.parser.BLTDEFInvokeActivity;
import it.unifi.dsi.blitese.parser.BLTDEFReceiveActivity;
import it.unifi.dsi.blitese.parser.BLTDEFSequenceActivity;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;
import it.unifi.dsi.blitese.parser.Node;
import it.unifi.dsi.blitese.parser.SimpleNode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class ActivityComponentFactory {

    private static final ActivityComponentFactory SINGLETON = new ActivityComponentFactory();

    private ActivityComponentFactory() {
    }

    /**
     * gets singleton instance
     * @return ActivityComponentFactory 
     */
    public static ActivityComponentFactory getInstance() {
        return SINGLETON;
    }

    public ActivityComponent makeRuntimeActivity(BltDefBaseNode bltDefNode,
            ExecutionContext context,
            ActivityComponent parentComponent,
            FlowExecutor executor) {
        try {
            DefClass actClass = provideActivityClass(bltDefNode);
            ActivityComponentBase activity = (ActivityComponentBase) actClass.clazz.newInstance();
            
            activity.setBltDefNode(actClass.def);
            activity.setContext(context);
            activity.setExecutor(executor);
            activity.setParentComponent(parentComponent);
            
            activity.init();

            return activity;

//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(ActivityComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
//            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ActivityComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ActivityComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }

    }
    
    private DefClass provideActivityClass(BltDefBaseNode bltDefNode) {
        
        if (bltDefNode instanceof BLTDEFInvokeActivity ) {
            
            return new DefClass(InvokeActivity.class, bltDefNode);
            
        } else if (bltDefNode instanceof BLTDEFReceiveActivity) {
            
            return new DefClass(ReceiveActivity.class, bltDefNode);
            
        } else if (bltDefNode instanceof ABTLDEFSequenceActivity ) {
            
            return new DefClass(SequenceActivity.class, bltDefNode);
            
        } else if (bltDefNode instanceof BLTDEFAssignActivity ) {
            
            return new DefClass(AssignActivity.class, bltDefNode);
            
        } else {
            
            //throw new RuntimeException("Not yet supported Activity " + bltDefNode);
            //we try fisiting the tree
            Node node = (Node) bltDefNode; 
            int nc = node.jjtGetNumChildren();
            
            for (int i = 0; i < nc; i++) {
                BltDefBaseNode kid = (BltDefBaseNode) node.jjtGetChild(i);
                DefClass dc = provideActivityClass(kid);
                if (dc != null) return dc;
            }
            
        }
        
        return null;
//        Class actClass = Class.forName(bltDefNode.provideRuntimeActivity());
    }
    
    private class DefClass  {
        Class clazz;
        BltDefBaseNode def;

        public DefClass(Class clazz, BltDefBaseNode def) {
            this.clazz = clazz;
            this.def = def;
        }
        
    }
}
