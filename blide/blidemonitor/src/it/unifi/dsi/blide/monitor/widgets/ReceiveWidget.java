/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blide.monitor.MonitorScene;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ReceiveActivity;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author panks
 */
public class ReceiveWidget extends IconActivityWidget {

    private MessageContainer mc;
    private ReceiveActivity receiveActivity;

    public ReceiveWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);

        receiveActivity = (ReceiveActivity) activity;

        mc = receiveActivity.getMatchingMessage();

    }

    @Override
    public void setUp() {
        super.setUp();
        if (mc != null) {
            MonitorScene monitorScene = (MonitorScene) getScene();
            monitorScene.addExchange(mc, this);
        }
    }



    @Override
    public String getIconPath() {
        return "it/unifi/dsi/blide/monitor/widgets/resources/receive.default.jpg";
    }

    @Override
    public String getLabel() {
        return "Receive";
    }

    
}
