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

/**
 * This is a wrapper interface on the message passed to/by the Engine
 * by/to the Environment.
 * 
 * @author panks
 */
public interface MessageContainer {

    enum Type {
        MESSAGE, 
        FAULT, 
        STATUS_DONE, 
        STATUS_ERROR
    };
    
    /**
     * gets ID
     *
     * @return String message container ID
     */
    public String getId();

    /**
     * gets content
     *
     * @return Object content
     */
    public Object getContent();
    
    
    /**
     * @return the type of the message
     */
    public Type getType();
    
}