/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import java.awt.Dimension;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author panks
 */
public class ExchangeFlowWidget extends Widget {

    public ExchangeFlowWidget(Scene scene) {
        super(scene);
        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 10));

        //a litle white space...
        Widget w = new Widget(scene);
        w.setMinimumSize(new Dimension(20, 50));
        addChild(w);
    }


}
