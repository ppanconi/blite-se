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
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.localenv.EngineLocation;
import it.unifi.dsi.blitese.localenv.gui.DesktopApplication;
import it.unifi.dsi.blitese.localenv.gui.DesktopView;
import it.unifi.dsi.blitese.localenv.gui.env.EnvModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.View;

/**
 *
 * @author panks
 */
public class DefinitionNode extends EnvBaseNode  {
    
    static ResourceMap resourceMap = DesktopApplication.getInstance().getContext().getResourceMap(DesktopView.class, View.class);
    
    private BliteDeploymentDefinition definition;
    private EngineLocation engineLocation;

    public DefinitionNode(EnvModel envModel, EngineLocation engineLocationo, BliteDeploymentDefinition definition) {
        super(definition.toString(), "envTree.df.icon", envModel);
        this.definition = definition;
        this.engineLocation = engineLocationo;
        if (definition.provideServiceInstance() != null) {
            setIconResName("envTree.rtr.icon");
        }
    }

    public BliteDeploymentDefinition getDefinition() {
        return definition;
    }

    @Override
    public JPopupMenu getPopupMenu() {
        
        
        if (definition.provideServiceInstance() != null) {
        
            JPopupMenu popup = new JPopupMenu();
            JMenuItem menuItem;
            menuItem = new JMenuItem("Launch an Instance", resourceMap.getIcon("popmenu.launch.icon"));
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    launchRtRInsatnce();
                }
            });
            popup.add(menuItem);

            return popup;
        }
        return null;
    }

    private void launchRtRInsatnce() {
        Engine engine = getEnvModel().getEnv().provideEngineAt(engineLocation);
        engine.startReadyToRunDefinition(definition.getBliteId());
    }
}
