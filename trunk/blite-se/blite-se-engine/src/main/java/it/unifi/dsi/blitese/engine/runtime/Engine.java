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
package it.unifi.dsi.blitese.engine.runtime;

import it.unifi.dsi.blitese.engine.definition.BliteProcessDef;


/**
 *
 * @author panks
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.E3664DFA-BCB9-1B85-E713-CE7F075EF6ED]
// </editor-fold> 
public interface Engine {
    
    /**
     * Add a Blite Process Definition to the Engine.
     * @param def
     * @param saName
     * @param suName
     */
    void addProcessDefinition(BliteProcessDef def, String saName, String suName);
    
    /**
     * Undeply a Blite Process Definition from the Engine.
     * @param id the Process Definition Unique id.
     */
    void removeProcessDefinition(Object id);

}

