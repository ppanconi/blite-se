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

package it.unifi.dsi.blitese.localenv.gui.log;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author panks
 */
public class SetUpLogging {
    
    
    static public void initializeLogging(JTextArea loggingArea) {
        LogManager m = LogManager.getLogManager();
        
        
        Logger root = m.getLogger("");
        JAreaHandler areaHandler = new JAreaHandler(loggingArea);
        
        root.addHandler(areaHandler);
    }

    public static void main(String[] args) {
        initializeLogging(new JTextArea());
    }
       
}
