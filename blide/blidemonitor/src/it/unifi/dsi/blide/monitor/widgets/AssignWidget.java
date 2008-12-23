/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author panks
 */
public class AssignWidget extends IconActivityWidget {

    public AssignWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);
    }

    @Override
    public String getIconPath() {
        return "it/unifi/dsi/blide/monitor/widgets/resources/assign.default.jpg";
    }

    @Override
    public String getLabel() {
        return "Assign";
    }

    
}
