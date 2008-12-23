/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import java.awt.Insets;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;
import org.openide.util.Utilities;

/**
 *
 * @author panks
 */
public class InstanceWidget extends ActivityWidget {

    private static final String START_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/start.default.jpg";
    private static final String END_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/end.default.jpg";

    private static final String CON_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/connector.gif";
    private static final org.netbeans.api.visual.border.Border BORDER_SHADOW_NORMAL =
            BorderFactory.createImageBorder (new Insets(6, 6, 6, 6), ImageUtilities.loadImage ("it/unifi/dsi/blide/monitor/widgets/resources/shadow_normal.png")); // NOI18N

    private Widget flowWidget;

    public InstanceWidget(Scene scene, ProcessInstance instance) {
        super(scene, instance);
        
        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 0));

        addChild(new ImageWidget(scene, ImageUtilities.loadImage (START_ICON)));
        addChild(new ImageWidget(scene, ImageUtilities.loadImage (CON_ICON)));

        flowWidget = new Widget(scene);
        flowWidget.setBorder(BorderFactory.createCompositeBorder(BORDER_SHADOW_NORMAL, BorderFactory.createEmptyBorder(20)));
        flowWidget.setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 5));

        addChild(flowWidget);
    }

    @Override
    public void add(Widget widget) {
        flowWidget.addChild(widget);
    }

    public void close() {
        addChild(new ImageWidget(getScene(), ImageUtilities.loadImage (CON_ICON)));
        addChild(new ImageWidget(getScene(), ImageUtilities.loadImage (END_ICON)));
    }
}
