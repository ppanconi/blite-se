/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blide.monitor.MonitorScene;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.InvokeActivity;
import java.awt.Color;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

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
        
        if (mc != null) {
            MonitorScene monitorScene = (MonitorScene) scene;
            LayerWidget connectionLayer = monitorScene.getConnectionLayer();
            Widget ew = monitorScene.addExchange(mc);

            ConnectionWidget edge = new ConnectionWidget (scene);
            edge.setSourceAnchor (AnchorFactory.createDirectionalAnchor (this, AnchorFactory.DirectionalAnchorKind.HORIZONTAL));
            edge.setTargetAnchor (AnchorFactory.createDirectionalAnchor (ew, AnchorFactory.DirectionalAnchorKind.HORIZONTAL));
            edge.setRouter (RouterFactory.createOrthogonalSearchRouter(
//                    monitorScene.getMainLayer(),
                    connectionLayer));

            edge.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
//        edge.setControlPointShape(PointShape.SQUARE_FILLED_BIG);
            edge.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
            edge.setLineColor(Color.LIGHT_GRAY);

            connectionLayer.addChild(edge);
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
