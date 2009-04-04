/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.PickActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ReceiveActivity;
import java.awt.Insets;
import java.util.List;
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
public class PickWidget extends ActivityWidget {

    private static final String CON_ICON = "it/unifi/dsi/blide/monitor/widgets/resources/picksep.png";
    private static final org.netbeans.api.visual.border.Border
             TOP_BORDER = BorderFactory.createImageBorder (new Insets(6, 6, 6, 6), ImageUtilities.loadImage ("it/unifi/dsi/blide/monitor/widgets/resources/flow_border.png")); // NOI18N

    private Widget[] opts;
    private PickActivity pa;

    public PickWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);
        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 0));

//        setBorder(TOP_BORDER);

        pa = (PickActivity) activity;

        opts = new Widget[pa.getRcvs().size()];
        int rcvOption = pa.getRcvOption();

        int i = 0;
        List<ReceiveActivity> rcvActs = pa.getRcvs();
        int totRcvs = rcvActs.size();
        for (ReceiveActivity receiveActivity : rcvActs) {

            Widget wi = new Widget(scene);
            opts[i] = wi;
            addChild(wi);
            if (i < totRcvs -1)
                addChild(new ImageWidget(getScene(), ImageUtilities.loadImage (CON_ICON)));

            if (i != rcvOption) {
                ReceiveWidget rw = new ReceiveWidget(scene, receiveActivity);
                wi.addChild(rw);
            }

            i++;
        }
    }

    @Override
    public void add(Widget widget) {
         int rcvOption = pa.getRcvOption();
         if (rcvOption >= 0) {
            Widget rcvWid = opts[rcvOption];
            rcvWid.addChild(widget);
         }
    }

}
