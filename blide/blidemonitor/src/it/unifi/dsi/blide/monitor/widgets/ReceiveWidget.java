/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author panks
 */
public class ReceiveWidget extends ActivityWidget {

    public ReceiveWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);
        addChild(new LabelWidget(scene, "Receive"));
    }

    
}
