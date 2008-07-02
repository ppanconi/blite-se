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

import it.unifi.dsi.blitese.engine.definition.BliteProcessDef;
import it.unifi.dsi.blitese.engine.runtime.Engine; 
import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import java.util.Hashtable;
import java.util.Map;

/**
 * This class is the implemetation of Bltie Engine the core of
 * blite-se.
 * 
 *
 * @author panks
 */

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.AF124637-05AF-E8EF-2AE2-36BB74D03751]
// </editor-fold> 
public class EngineImp implements Engine {
    
    private Object mDeployLock = new Object(); //onjext used as monitor in un/deployment phase
    private boolean mNewAddedDep = false; //one state marker for eventually persistence synch.
    /**
     * Deployed Blite program definitions
     */
    private Map<Object, BliteProcessDef> mProcessDefs = new Hashtable<Object, BliteProcessDef>();
    
    /**
     * The Process Managers, one to one Blite Definition
     */
    private Map<BliteProcessDef, ProcessManager> mManagers = new Hashtable<BliteProcessDef, ProcessManager>();
    
    

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.95FD3D33-A130-6421-5A9C-C5E4D303DE4C]
    // </editor-fold> 
    public EngineImp () {
    }

    public void addProcessDefinition(BliteProcessDef bliteDef, String saName, String suName) {
        
        synchronized (mDeployLock) {
            // String id = bpelProcess.getBPELId();
            Object id = bliteDef.getBliteId();
            if (mProcessDefs.get(id) != null) {
                throw new RuntimeException("BPCOR-6025: Business processes with duplicate id are " +
                        "not allowed in an engine. Business Process " + id.toString() + " id already registered");
            }

            mProcessDefs.put(id, bliteDef);

            ProcessManager processManager = new ProcessManagerImp(bliteDef, this, saName, suName);
            Object retObj = mManagers.put(bliteDef, processManager);
            if (retObj != null) {
                throw new RuntimeException("Fatal Error: this process is already loaded process Id: " + bliteDef.getBliteId());
            }
            mNewAddedDep = true;
        }

    }
    
    

}

