/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;

/**
 *
 * @author panks
 */
public class SequenceWidget extends ActivityWidget {

    private static final String CON_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/arrow.gif";

    private int numberOfChilds = 0;

    public SequenceWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);

        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 0));
    }

    @Override
    public void add(Widget widget) {
        if (numberOfChilds > 0) {
            addChild(new ImageWidget(getScene(), ImageUtilities.loadImage (CON_ICON)));
        }

        addChild(widget);

        numberOfChilds++;
    }

    
}
