/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blide.monitor.MonitorScene;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.InvokeActivity;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author panks
 */
public class InvokeWidget extends IconActivityWidget {

    private InvokeActivity invokeAct;
    private MessageContainer mc;

    public InvokeWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);
        invokeAct = (InvokeActivity) activity;

        mc = invokeAct.getMessageContainer();
        
        
    }

    @Override
    public void setUp() {
        if (mc != null) {
            MonitorScene monitorScene = (MonitorScene) getScene();
            monitorScene.addExchange(mc, this);
        }
    }

    @Override
    public String getIconPath() {
        return "it/unifi/dsi/blide/monitor/widgets/resources/invoke.default.jpg";
    }

    @Override
    public String getLabel() {
        return "Invoke";
    }

    
}
