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

import it.unifi.dsi.blitese.engine.definition.BliteDeploymentDefinition;
import it.unifi.dsi.blitese.engine.definition.imp.BliteDeploymentDefinitionImp;
import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;
import it.unifi.dsi.blitese.engine.runtime.imp.EngineImp;
import it.unifi.dsi.blitese.parser.AServiceElement;
import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BLTDEFDeployment;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class LocalEnvironment {
    
    private static final Logger LOGGER = Logger.getLogger(LocalEnvironment.class.getName());
    
    private Map<URL, BLTDEFCompilationUnit> mCompUnits = new HashMap<URL, BLTDEFCompilationUnit>(); 
    
    //
    private Map<EngineLocation, Engine> mLocToEngine = new HashMap<EngineLocation, Engine>();
    
    private Map<String, Engine> mServiceNameToEngine = new HashMap<String, Engine>();
    
    private LocalEngineChannel channel;
    
    private Object deploymentLock = new Object();

    public LocalEnvironment() {
        channel = new LocalEngineChannel(this);
    }
    
    /**
     * Add a compiletion unit if not yet present in the Enviroment.
     * 
     * @param compilationUnit to add
     */
    public void addCompilationUnit(BLTDEFCompilationUnit compilationUnit)  throws IncompatibleCompUnitException {
        
        URL res = compilationUnit.getResource();
        
        BLTDEFCompilationUnit pc = mCompUnits.get(res);
        if (pc == null) {
            mCompUnits.put(res, compilationUnit);
            
            synchronized (deploymentLock) {
                LOGGER.info("Start deploy process for " + res);
            
                int depCount = 0;
                //for each dep definition 
                for (BLTDEFDeployment deploy : compilationUnit.getDeployments()) {
                    depCount++;
                    //to starting coding we have a deployment defines a location
                    EngineLocation loc = new EngineLocation(res.toString() + ":" + depCount);

                    Engine engine = mLocToEngine.get(loc);

                    if (engine == null) {
                        engine = new EngineImp();
                        engine.setChannel(channel);
                        mLocToEngine.put(loc, engine);
                        LOGGER.info("Created Engine at location " + loc);
                    } 


                    //we have to map the service name defined in 
                    //the current deploy to the relative Engine
                    int servCount = 0;
                    for (AServiceElement aServiceEle : deploy.provideAllServiceElement()) {
                        servCount++;

                        for (String serviceName : aServiceEle.provideAllServiceName()) {

                            Engine poe = mServiceNameToEngine.get(serviceName);
                            if (poe == null) {
                                poe = engine;
                                mServiceNameToEngine.put(serviceName, engine);
                            } else if (!poe.equals(engine)) {
                                throw new IncompatibleCompUnitException("The compilation unit is not compatible with " +
                                        "the actual Eviroment. It defines the service " + serviceName + 
                                        " yet present in the Enviroment in different location");
                            }
                        }


                        //TODO use a ServiceElement name instaed of servCount 
                        String deployId = loc.toString() + "/" + servCount; 
                        //now we deploy the ServiceElement to the Engine.
                        BliteDeploymentDefinition deploymentDefinition =
                                new BliteDeploymentDefinitionImp(aServiceEle, deployId);

                        engine.deployProcessDefinition(deploymentDefinition, null, null);
                        
                        LOGGER.info("Deployed " + deployId + " at " + loc);
                    }
                }
                LOGGER.info("Finished deploy process for " + res);
            }
        }
        
    }
    
    
    public Engine provideServiceEngine(ServiceIdentifier serviceId) {
        
        synchronized (deploymentLock) {
            //:( The portId in blite it's the same of Endpont
            //this's a strange view of WS world... 
            String serviceName = serviceId.provideStringServiceName();

            Engine engine = mServiceNameToEngine.get(serviceName);
            if (engine == null) 
                throw new IllegalStateException("Service " + serviceName + " not have a Engine!! THIS IS IMPLEMETATION BUG!!");

            return engine;
        }
    }
    
    
    
    /**
     * Utility mathod for testing. It starts all the readyToRun Definition
     * deployed into the Engine. It create one instance for readyToRun definition.
     */
    public void startAllReadyToRunDefinitions() {
        for (Engine engine : mLocToEngine.values()) {
            engine.startAllReadyToRunDefinitions();
        }
    }
    
    public List<EngineLocation> provideSortLocation() {
        return null;
    }
}
