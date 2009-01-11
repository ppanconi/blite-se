/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.activities.imp.ConditionalActivity;
import java.awt.Color;
import org.netbeans.api.visual.border.BorderFactory;
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
public class ConditionalWidget extends ActivityWidget {

    private static final String TRUE_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/condition.true.jpg";
    private static final String FALSE_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/condition.false.jpg";
    private static final String DEFOULT_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/condition.default.jpg";

    public ConditionalWidget(Scene scene, ConditionalActivity activity) {
        super(scene, activity);
        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 4));
        setBorder(BorderFactory.createRoundedBorder(5, 5, Color.WHITE, Color.GRAY));

        Widget testIcon = null;
        Widget testLabel = null;

        if (activity.isTested() && activity.getTestValue())  {
            testIcon = new ImageWidget(getScene(), ImageUtilities.loadImage(TRUE_ICON));
            testLabel = new LabelWidget(scene, "True");
        } else if (activity.isTested() && !activity.getTestValue())  {
            testIcon = new ImageWidget(getScene(), ImageUtilities.loadImage(FALSE_ICON));
            testLabel = new LabelWidget(scene, "False");
        } else {
            testIcon = new ImageWidget(getScene(), ImageUtilities.loadImage(DEFOULT_ICON));
        }

        add(testIcon);
        if (testLabel != null) add(testLabel);

    }

    

}
