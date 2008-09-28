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

import it.unifi.dsi.blitese.engine.runtime.ExecutionContext;
import it.unifi.dsi.blitese.engine.runtime.MessageContent;
import it.unifi.dsi.blitese.parser.ABltValueHolder;
import it.unifi.dsi.blitese.parser.BLTDEFInvokeActivity;

/**
 *
 * @author panks
 */
public class MessageContentFactory {

    static public MessageContent createInvokeMC(ExecutionContext context, BLTDEFInvokeActivity activity) {
        
        ABltValueHolder vh = activity.getParams();
        
        Object o = RuntimeValueFactory.makeRuntimeValue(vh, context);
        
        if (o instanceof String) {
            String string = (String) o;
            return createStringMC(string);
        } 
        
        
        throw new RuntimeException("Not Yet supported");
    }
    
    static public MessageContent createStringMC(String string) {
        StringBuffer s = new StringBuffer(string);
        return new MessageContentImp(s);
    }
    
    
    
    
}
