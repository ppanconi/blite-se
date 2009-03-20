/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;

/**
 *
 * @author panks
 */
public class ExchangeWidget extends Widget {

    private static final String ICON_PATH = "it/unifi/dsi/blide/monitor/widgets/resources/exchange.default.jpg";
    private Object messageId;
    private List<ConnectionWidget> connections = new ArrayList<ConnectionWidget>();

    public ExchangeWidget(Scene scene, Object exchangeId) {
        super(scene);
        this.messageId = exchangeId;
//        setPreferredSize(new Dimension(80, 80));
//        setMinimumSize(new Dimension(80, 80));
//        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 2));
//        setBorder(BorderFactory.createRoundedBorder(5, 5, Color.WHITE, Color.GRAY));

        addChild(new ImageWidget(scene, ImageUtilities.loadImage (ICON_PATH)));
//        addChild(new LabelWidget(scene, exchangeId.toString()));

    }

    public Object getMessageContainer() {
        return messageId;
    }

    public void addConnection(ConnectionWidget connection) {
        connections.add(connection);
    }

    public void light() {

        for (ConnectionWidget connectionWidget : connections) {
            connectionWidget.setLineColor(Color.RED);
        }
    }

    public void unlight() {

        for (ConnectionWidget connectionWidget : connections) {
            connectionWidget.setLineColor(Color.LIGHT_GRAY);
        }
    }

}
