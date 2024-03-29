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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
    
    private final Object deploymentLock = new Object();

    private SingleStepper stepper = new SingleStepper();

    public SingleStepper getStepper() {
        return stepper;
    }

    public LocalEnvironment() {
        channel = new LocalEngineChannel(this);
    }

    public void clear() {
        mCompUnits = new HashMap<URL, BLTDEFCompilationUnit>();
        mLocToEngine = new HashMap<EngineLocation, Engine>();
        mServiceNameToEngine = new HashMap<String, Engine>();
        channel = new LocalEngineChannel(this);

        fire();
    }


    /**
     * Add a compiletion unit if not yet present in the Enviroment.
     * 
     * @param compilationUnit to add
     */
    public void addCompilationUnit(BLTDEFCompilationUnit compilationUnit) throws IncompatibleCompUnitException {
        
        URL res = compilationUnit.getResource();
        
        BLTDEFCompilationUnit pc = mCompUnits.get(res);
        if (pc == null) {
            
            synchronized (deploymentLock) {
                LOGGER.info("Start deploy process for " + res);
            
//                int depCount = 0;
                //for each dep definition 
                for (BLTDEFDeployment deploy : compilationUnit.getDeployments()) {
//                    depCount++

                    EngineLocation loc = EngineLocation.make(compilationUnit, deploy);

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
                            } else if (!poe.equals(engine)) {
                                throw new IncompatibleCompUnitException("The compilation unit is not compatible with " +
                                        "the actual Eviroment. It defines the service " + serviceName + 
                                        " yet present in the Enviroment in different location");
                            }
                        }


                        //TODO use a ServiceElement name instaed of servCount 
//                        String deployId = loc.toString() + "/" + servCount;
                        Object deployId = deploy.provideDeployId(
                                loc.getLocationName(), aServiceEle, servCount);

                        //now we deploy the ServiceElement to the Engine.
                        BliteDeploymentDefinition deploymentDefinition =
                                new BliteDeploymentDefinitionImp(aServiceEle, deployId);

                        //OK we can deploy the deployment definition a store the service names
                        //in local chaches

                        //FIXME WE HAVE TO DEPLOY THE DEFINITION AFTER WE ARE SURE
                        //THE ALL THE COMPILATION UNIT IS VALID
                        engine.deployProcessDefinition(deploymentDefinition, null, null);

                        // -- we set a local monitor on the definition
                        engine.setMonitor(new LocalDefinitionMonitor(deploymentDefinition));

                        for (String serviceName : aServiceEle.provideAllServiceName()) {
                            mServiceNameToEngine.put(serviceName, engine);
                        }
                        
                        LOGGER.info("Deployed " + deployId + " at " + loc);
                    
                    } // -- end Service for a deployment

                } // -- end all deployment

                LOGGER.info("Finished deploy process for " + res);

            } // -- End Critic Section
            
            mCompUnits.put(res, compilationUnit);
            // -- notifay the changes in deploiments
            fire();
        }

    }

    public void removeCompilationUnit(BLTDEFCompilationUnit compilationUnit) {
        URL res = compilationUnit.getResource();

        synchronized (deploymentLock) {
            BLTDEFCompilationUnit pc = mCompUnits.remove(res);
            if (pc != null) {

                for (BLTDEFDeployment deploy : compilationUnit.getDeployments()) {

                    EngineLocation loc = EngineLocation.make(compilationUnit, deploy);
                    Engine engine = mLocToEngine.get(loc);

                    int index = 0;
                    for (AServiceElement aServiceEle : deploy.provideAllServiceElement()) {
                        index++;

                        Object deployId = deploy.provideDeployId(loc.getLocationName(), aServiceEle, index);

                        engine.removeProcessDefinition(deployId);

                        for (String serviceName : aServiceEle.provideAllServiceName()) {
                                mServiceNameToEngine.remove(serviceName);
                        }
                    }
                }

                //-- we notify the deployment change
                fire();
            }
        }
    }

    public void synchCompilationUnit(BLTDEFCompilationUnit compilationUnit)  throws IncompatibleCompUnitException  {

        removeCompilationUnit(compilationUnit);
        addCompilationUnit(compilationUnit);
        
    }

    public BLTDEFCompilationUnit getInsatlledUnit(URL url) {
        return mCompUnits.get(url);
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
    
    //TODO inprove this has not perfomance
    public List<EngineLocation> provideSortLocations() {
        List<EngineLocation> locs = new ArrayList<EngineLocation>(mLocToEngine.keySet());
        Collections.sort(locs);
        return locs;
    }
    
    public Engine provideEngineAt(EngineLocation location) {
        return mLocToEngine.get(location);
    }

    // -------------------------------------------------------------------------
    private static List <ChangeListener> listeners =
            Collections.<ChangeListener>synchronizedList(new
            LinkedList <ChangeListener>());
    public void addChangeListener (ChangeListener l) {
        listeners.add (l);
    }

    public void removeChangeListener (ChangeListener l) {
        listeners.remove (l);
    }

    private void fire() {
        ChangeListener[] l = listeners.toArray (new ChangeListener[0]);
        for (int i=0; i < l.length; i++) {
            l[i].stateChanged (new ChangeEvent (this));
        }
    }

}
