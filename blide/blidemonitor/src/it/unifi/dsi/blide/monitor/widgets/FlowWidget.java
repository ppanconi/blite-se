/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import java.awt.Insets;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.ImageUtilities;

/**
 *
 * @author panks
 */
public class FlowWidget extends ActivityWidget {

     private static final org.netbeans.api.visual.border.Border
             TOP_BORDER = BorderFactory.createImageBorder (new Insets(6, 6, 6, 6), ImageUtilities.loadImage ("it/unifi/dsi/blide/monitor/widgets/resources/flow_border.png")); // NOI18N

    public FlowWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);

        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 0));

        setBorder(TOP_BORDER);
    }

    
}
