/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.engine.runtime.activities.imp;

import it.unifi.dsi.blitese.engine.definition.ActivityComponentFactory;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.ProcessManager;
import it.unifi.dsi.blitese.parser.BLTDEFPickActivity;
import it.unifi.dsi.blitese.parser.BLTDEFPickActivity.PickSequence;
import it.unifi.dsi.blitese.parser.BLTDEFSequenceActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class PickActivity extends ActivityComponentBase {

    static Logger log = Logger.getLogger(PickActivity.class.getName());

    private BLTDEFPickActivity pickDef;
    private ProcessManager manager;

    private Map<ReceiveActivity, BLTDEFPickActivity.PickSequence> mOptions =
            new HashMap<ReceiveActivity, BLTDEFPickActivity.PickSequence>();

    private List<ReceiveActivity> rcvs = new ArrayList<ReceiveActivity>();

    private boolean reviced = false;
    private boolean completed = false;
    private int rcvOption = -1;

    @Override
    public void init() {
        super.init();
        pickDef = (BLTDEFPickActivity) getBltDefNode();
        manager = getContext().getProcessInstance().getManager();

        for (BLTDEFPickActivity.PickSequence pickSequence : pickDef.getOptions()) {

            ReceiveActivity reci = (ReceiveActivity) ActivityComponentFactory.getInstance().makeRuntimeActivity(pickSequence.receiveActivity,
                    getContext(), this, getExecutor());

            rcvs.add(reci);
            mOptions.put(reci, pickSequence);
        }
    }

    public boolean doActivity() {


        if (!reviced) {

            synchronized (manager.getDefinitionProcessLevelLock()) {

                if (getContext().isInAFaultedBranch()) {
                    log.info("Terminated activity " + this);
                    flowParent();
                    return true;
                }

                int i = 0;
                for (ReceiveActivity receiveActivity : rcvs) {
                    
                    if (receiveActivity.matchAMessage(false) != null) {
                        //we hava a message
                        reviced = true;
                        rcvOption = i;
                        manager.getEngine().removeFlowFromWaiting(getExecutor());

                        BLTDEFPickActivity.PickSequence pickSequence = mOptions.get(receiveActivity);
                        BLTDEFSequenceActivity seqDef =
                                new BLTDEFSequenceActivity(pickSequence.receiveActivity,
                                                           pickSequence.activity);

                        ActivityComponent seqAct = ActivityComponentFactory.getInstance().makeRuntimeActivity(seqDef,
                                                                           getContext(), this, getExecutor());

                        getExecutor().setCurrentActivity(seqAct);
                        return true;

                    } else {
                        //we wait for the message
                        manager.getEngine().addFlowWaitingEvent(getExecutor(), receiveActivity.getIcek());
                    }
                    i++;
                }
            }

            return false;

        } else {
            completed = true;
            flowParent();
            return true;
        }
    }

    public int getRcvOption() {
        return rcvOption;
    }

    public boolean isReviced() {
        return reviced;
    }

    public boolean isCompleted() {
        return completed;
    }

    public List<ReceiveActivity> getRcvs() {
        return rcvs;
    }

    
}
