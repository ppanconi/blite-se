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

package it.unifi.dsi.blitese.localenv;

import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;
import it.unifi.dsi.blitese.engine.runtime.imp.EngineImp;
import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BLTDEFDeployment;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author panks
 */
public class LocalEnvironment {
    
    private Map<URL, BLTDEFCompilationUnit> mCompUnits = new HashMap<URL, BLTDEFCompilationUnit>(); 
    
    //
    private Map<EngineLocation, Engine> mLocToEngine = new HashMap<EngineLocation, Engine>();
    
    private LocalEngineChannel channel;

    public LocalEnvironment() {
        channel = new LocalEngineChannel(this);
    }
    
    /**
     * Add a compiletion unit if not yet present in the Enviroment.
     * 
     * @param compilationUnit to add
     */
    public void addCompilationUnit(BLTDEFCompilationUnit compilationUnit) {
        
        URL res = compilationUnit.getResource();
        
        if (mCompUnits.get(res) != null) {
            mCompUnits.put(res, compilationUnit);
            
            //for each dep definition 
            for (BLTDEFDeployment deploy : compilationUnit.getDeployments()) {
                
                //to starting coding we have a deployment defines a location
                EngineLocation loc = new EngineLocation();
                
                Engine engine = mLocToEngine.get(loc);
                
                if (engine == null) {
                    engine = new EngineImp();
                    engine.setChannel(channel);
                    mLocToEngine.put(loc, engine);
                }
                
                
            }
        }
        
    }
    
    
    public Engine provideServiceEngine(ServiceIdentifier serviceId) {
        throw new RuntimeException("Not implemeted yet");
    }
    
    
}
