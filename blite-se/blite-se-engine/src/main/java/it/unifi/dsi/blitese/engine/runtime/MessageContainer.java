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

    public enum Type {
        MESSAGE, 
        FAULT, 
        STATUS_DONE, 
        STATUS_ERROR
    };
    
    /**
     * gets Id of the message exchange sequence where the actual
     * message belong.
     *
     * @return String message container ID
     */
    public Object getId();

    public void setId(Object id);
    /**
     * gets content
     *
     * @return Object content
     */
    public Object getContent();
    
    
    /**
     * @return the type of the message
     */
    public MessageContainer.Type getType();
    
    /**
     * @return An object rappresenting a logic message id at application level
     */
    public Object getApplicationTraceId();
    
}
