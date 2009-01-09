/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.imp.ABaseContext.ContextState;
import java.awt.Insets;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;

/**
 *
 * @author panks
 */
public class InstanceWidget extends ActivityWidget {

    private static final String START_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/start.default.jpg";
    private static final String END_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/end.default.jpg";
    private static final String END_OK_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/end.completed.jpg";
    private static final String END_KO_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/end.faulted.jpg";

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

    public ProcessInstance getInstance() {
        return (ProcessInstance) getActivity();
    }

    public void close() {
        addChild(new ImageWidget(getScene(), ImageUtilities.loadImage (CON_ICON)));

        ProcessInstance instance = getInstance();
        ContextState instanceState = instance.getState();

        if (instanceState == ContextState.COMPLETED) {

            addChild(new ImageWidget(getScene(), ImageUtilities.loadImage (END_OK_ICON)));
        } else if (instanceState == ContextState.FAULTED ||
                instanceState == ContextState.TEMINATED) {

            addChild(new ImageWidget(getScene(), ImageUtilities.loadImage (END_KO_ICON)));
        } else {

            addChild(new ImageWidget(getScene(), ImageUtilities.loadImage (END_ICON)));
        }
    }
}
