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
import it.unifi.dsi.blitese.parser.BLTDEFExpression;
import it.unifi.dsi.blitese.parser.BLTDEFInvParam;
import it.unifi.dsi.blitese.parser.BLTDEFInvParams;
import it.unifi.dsi.blitese.parser.BLTDEFInvokeActivity;

/**
 *
 * @author panks
 */
public class MessageContentFactory {

    static public MessageContent createInvokeMC(ExecutionContext context, BLTDEFInvokeActivity activity) {
        
        BLTDEFInvParams params = activity.getParams();
        
        int n = params.getActualParams().size();
        Object[] parts = new Object[n];
        int i = 0;
        for (BLTDEFInvParam param : params.getActualParams()) {

             BLTDEFExpression expr = (BLTDEFExpression) param.jjtGetChild(0);
             Object exprValue = ExpressionInterpreter.evaluate(context, expr);
//            Object exprValue = RuntimeValueFactory.makeRuntimeValue(param, context);

             //JUST TO RUN-LOCALLY
//            if (exprValue instanceof String) {
//                String s = (String) exprValue;
//                String v = new String(s);
//                parts[i++] = v;
//            } else {
//                throw new RuntimeException("Not Yet supported");
//            }
             parts[i++] = exprValue;


        }
        
        return new MessageContentImp(parts);
        
    }
    
    static public MessageContent createStringMC(String string) {
        String s = new String(string);
        return new MessageContentImp(s);
    }
    
    
    
    
}
