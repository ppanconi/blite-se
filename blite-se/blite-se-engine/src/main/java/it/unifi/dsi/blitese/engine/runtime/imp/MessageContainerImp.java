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

package it.unifi.dsi.blitese.engine.runtime.imp;

import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer.Type;

/**
 *
 * @author panks
 */
public class MessageContainerImp implements MessageContainer {
    
    private Object id;
    private Object content;
    private MessageContainer.Type type;
    private Object applicationTraceId;

    public MessageContainerImp(Object content, Type type, Object applicationTraceId) {
        this.content = content;
        this.type = type;
        this.applicationTraceId = applicationTraceId;
    }

    public MessageContainerImp(Object id, Object content, Type type, Object applicationTraceId) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.applicationTraceId = applicationTraceId;
    }

    public Object getApplicationTraceId() {
        return applicationTraceId;
    }

    public Object getContent() {
        return content;
    }

    public Object getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setId(Object id) {
        this.id = id;
    }
    
    
}
