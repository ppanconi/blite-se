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
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.ProcessManager; 
import it.unifi.dsi.blitese.parser.BLTDEFProcessInstanceMY;

/**
 * 
 *
 * @author panks
 */

public class ProcessManagerImp implements ProcessManager {
    
    private BliteProcessDef mBliteProcessDef;
    private Engine mEngine;
    private String mSaName;
    private String mSuName;

    public ProcessManagerImp(BliteProcessDef bliteProcessDef, Engine engine, String saName, String suName) {
        mBliteProcessDef = bliteProcessDef;
        mEngine = engine;
        
        mSaName = (saName != null) ? saName : "unavailable";
	mSuName = (suName != null) ? suName : "unavailable";

        //if the static definition conteins same ready to run instances
        //we start with them
        for (BLTDEFProcessInstanceMY instDef : bliteProcessDef.getDefProcessInstances()) {
            
            ProcessInstance processInstance = new ProcessInstanceImp(mEngine, this, null, instDef);
            processInstance.activete();
            
        }
        
    }

   
    
}

