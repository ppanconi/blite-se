/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author panks
 */
public class FlowWidget extends ActivityWidget {

    public FlowWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);

        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 10));
    }

    
}
