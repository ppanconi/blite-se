/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import java.awt.Dimension;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;

/**
 *
 * @author panks
 */
public class ExchangeWidget extends Widget {

    private static final String ICON_PATH = "it/unifi/dsi/blide/monitor/widgets/resources/exchange.default.jpg";

    public ExchangeWidget(Scene scene, Object exchangeId) {
        super(scene);

//        setPreferredSize(new Dimension(80, 80));
//        setMinimumSize(new Dimension(80, 80));
//        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 2));
//        setBorder(BorderFactory.createRoundedBorder(5, 5, Color.WHITE, Color.GRAY));

        addChild(new ImageWidget(scene, ImageUtilities.loadImage (ICON_PATH)));
//        addChild(new LabelWidget(scene, exchangeId.toString()));

    }


}
