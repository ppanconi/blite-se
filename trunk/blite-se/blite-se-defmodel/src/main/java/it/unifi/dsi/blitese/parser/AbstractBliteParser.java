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
public class AbstractBliteParser {
    
    static Map<AServiceElement, Map<String, Object>> sSymbolTables = new HashMap<AServiceElement, Map<String, Object>>();
    static AServiceElement currentServEle;
    
    //
    static Map<String, AServiceElement> mServiceNameToServices = new HashMap<String, AServiceElement>();
    
    public static void cleanTables() {
        sSymbolTables = new HashMap<AServiceElement, Map<String, Object>>();
        currentServEle = null;
        mServiceNameToServices = new HashMap<String, AServiceElement>();
    }
    
//    static public AbstractBliteParser provideInstance(InputStream stream) {
//        BliteParser bliteParser = new BliteParser(stream);
//        return bliteParser;
//    }
    
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
    
    /**
     * Add the current recive Activity definition to the current Service Element Definition
     * It checks also the deployments well-formed constrant on the definition.
     * 
     * And Also check the conformity of actual operration with the eventual previous
     * defined one.
     * 
     * @param receiveActivity
     * @throws it.unifi.dsi.blitese.parser.ParseException
     */
    public static void addPort(BLTDEFReceiveActivity receiveActivity) throws ParseException {
        
        String serviceName = receiveActivity.getPartners().getServiceName();
        
        AServiceElement previous = mServiceNameToServices.get(serviceName);
        if (previous == null) {
            previous = currentServEle;
            mServiceNameToServices.put(serviceName, currentServEle);
        } else if (!previous.equals(currentServEle)) {
            
            Token t = receiveActivity.getPartners().getRecPartToken();
            throw new ParseException("The deployments are not well-formated. " +
                    "The service name " + serviceName + " definined at line " + t.beginLine + " , column " + t.beginColumn + " is used in other Deploy/ServiceInsatance definition");
        }
        
        boolean portConformity = currentServEle.checkOperationConfomity(receiveActivity);
        
        if (!portConformity) {
            Token t = receiveActivity.getOperation().getToken();
            throw new ParseException("The operation at " +
                     + t.beginLine + " , column " + t.beginColumn + " has not a conform signature to previous ones definitions");
        }
        
        currentServEle.addPort(receiveActivity);
        
        
    }
    
    public static void markReciveAsCreateInst(BLTDEFReceiveActivity receiveActivity) {
        
        BLTDEFServiceDef processDef = (BLTDEFServiceDef) currentServEle;
        
        processDef.markAsCreateInstance(receiveActivity);
        
    }
    
    
    
}
