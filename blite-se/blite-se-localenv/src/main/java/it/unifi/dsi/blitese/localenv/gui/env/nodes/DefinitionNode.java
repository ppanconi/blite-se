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

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.localenv.gui.env.EnvModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author panks
 */
public class DefinitionNode extends EnvBaseNode implements ActionListener {
    
    private BliteDeploymentDefinition definition;

    public DefinitionNode(EnvModel envModel, BliteDeploymentDefinition definition) {
        super(definition.toString(), null, envModel);
        this.definition = definition;
    }

    public BliteDeploymentDefinition getDefinition() {
        return definition;
    }

    @Override
    public JPopupMenu getPopupMenu() {
        
        
        if (definition.provideServiceInstance() != null) {
        
            JPopupMenu popup = new JPopupMenu();
            JMenuItem menuItem;
            menuItem = new JMenuItem("Start");
            menuItem.addActionListener(this);
            popup.add(menuItem);

            return popup;
        }
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        
    }

}
