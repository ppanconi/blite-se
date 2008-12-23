/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import java.awt.Color;
import java.awt.Dimension;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.ImageUtilities;

/**
 *
 * @author panks
 */
public abstract class IconActivityWidget extends ActivityWidget {

    public IconActivityWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);

        setPreferredSize(new Dimension(80, 80));
        setMinimumSize(new Dimension(80, 80));
        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 2));
        setBorder(BorderFactory.createRoundedBorder(5, 5, Color.WHITE, Color.GRAY));

        addChild(new ImageWidget(scene, ImageUtilities.loadImage (getIconPath())));
        addChild(new LabelWidget(scene, getLabel()));
        
    }

    public abstract String getIconPath();
    
    public abstract String getLabel();
    

}
