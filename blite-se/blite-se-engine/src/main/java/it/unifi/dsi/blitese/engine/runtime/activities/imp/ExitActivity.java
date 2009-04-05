/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.engine.runtime.activities.imp;

import it.unifi.dsi.blitese.engine.runtime.Fault;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class ExitActivity extends ActivityComponentBase {

    static Logger LOGGER = Logger.getLogger(ThrowActivity.class.getName());

    public boolean doActivity() {
        LOGGER.info("EXIT FROM THE INSTANCE");
        getContext().getProcessInstance().notifyFault(new Fault() {});
        flowParent();
        return true;
    }

}
