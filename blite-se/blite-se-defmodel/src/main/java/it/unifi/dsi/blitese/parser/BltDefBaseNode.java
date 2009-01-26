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

import java.util.Map;

/**
 *
 * @author panks
 */
public class BltDefBaseNode {
    
    private Map<String, Object> boundSymbs;
    
    public String provideRuntimeActivity() {
        return null;
    }

    public Map<String, Object> getBoundSymbs() {
        return boundSymbs;
    }

    public void setBoundSymbs(Map<String, Object> boundSymbs) {
        this.boundSymbs = boundSymbs;
    }

    //26-01-09 added to inprove the visual representation of instaces
    private boolean visible = true;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


}
