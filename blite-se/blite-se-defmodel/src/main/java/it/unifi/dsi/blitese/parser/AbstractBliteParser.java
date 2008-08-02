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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author panks
 */
public abstract class AbstractBliteParser {
    
    static Map<AServiceElement, Map<String, Object>> sSymbolTables = new HashMap<AServiceElement, Map<String, Object>>();
    static AServiceElement currentServEle;
    
    static public AbstractBliteParser provideInstance(InputStream stream) {
        BliteParser bliteParser = new BliteParser(stream);
        return bliteParser;
    }
    
    static void jjtreeOpenNodeScope(Node n) {
        
        if (n instanceof AServiceElement) {
            AServiceElement aServiceElement = (AServiceElement) n;
            
            currentServEle = aServiceElement;
            sSymbolTables.put(currentServEle, new HashMap<String, Object>());
        }
        
        //if we are inside a service we put the current sysTab into the node
        BltDefBaseNode bltNode = (BltDefBaseNode) n;
        
        if (currentServEle != null) {
            bltNode.setBoundSymbs(sSymbolTables.get(currentServEle));
        }
    }
    
    static void jjtreeCloseNodeScope(Node n) {
    
        if (n instanceof AServiceElement) {
            AServiceElement aServiceElement = (AServiceElement) n;
            
            if (currentServEle != aServiceElement)
                throw new IllegalStateException("");
                
            currentServEle = null;
        }
    }

    
    abstract public BltDefBaseNode parse() throws ParseException;

    
}
