/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import java.awt.Color;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author panks
 */
public class ScopeWidget extends ActivityWidget {

    private Widget mainActConteiner;

    public ScopeWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);
        setBorder(BorderFactory.createDashedBorder(Color.GRAY, 5, 5, true));
        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 5));

        mainActConteiner = new Widget(scene);
        mainActConteiner.setBorder(BorderFactory.createEmptyBorder(5));

        addChild(mainActConteiner);
    }

    @Override
    public void add(Widget widget) {
        mainActConteiner.addChild(widget);
    }

    public void addProteced(Widget widget) {
        addChild(widget);
    }
    
}
