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
public class ActivityWidget extends Widget {

    protected ActivityComponent activity;

    public ActivityWidget(Scene scene, ActivityComponent activity) {
        super(scene);
        this.activity = activity;
        setLayout(LayoutFactory.createOverlayLayout());
//        setBorder(BorderFactory.createDashedBorder(Color.CYAN, 1, 1));
    }

    public void add(Widget widget) {
        addChild(widget);
    }

    /**
     * This notifay the possibility to update widget state.
     * 
     * Return true if repaint is needed.
     * 
     * @return boolean the need to repaint the widget.
     */
    public boolean upadte() {
        return false;
    }
}
