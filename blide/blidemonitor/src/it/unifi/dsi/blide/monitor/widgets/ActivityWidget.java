/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author panks
 */
public class ActivityWidget extends Widget {

    protected ActivityComponent activity;

    public ActivityWidget(Scene scene, ActivityComponent activity) {
        super(scene);
        this.activity = activity;
    }


}
