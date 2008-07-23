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

import it.unifi.dsi.blitese.parser.BLTDEFProcessInstanceMY;
import java.util.Set;

/**
 * This the interface for the static definition of a Blite process (program).
 *
 * @author panks
 */

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.37FF4355-A40B-87F0-09FC-68525EE2AC5E]
// </editor-fold> 
public interface BliteProcessDef {

    /**
     * An unique (probably absolute) id for the Blite Process Definition
     * //TODO probably to change to an xml QName
     * @return Object (in future a QNAme)
     */
    Object getBliteId();
    
    /**
     * @return Set<BltDefServiceInstance> the set of statically defined 
     * blite processes instaces.
     */
    Set<BLTDEFProcessInstanceMY> getDefProcessInstances();
    
}

