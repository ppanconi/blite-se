/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.monitor;

import it.unifi.dsi.blide.monitor.widgets.ActivityWidget;
import it.unifi.dsi.blide.monitor.widgets.WidgetFactory;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.localenv.LocalInstanceMonitor;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author panks
 */
public class MonitorScene extends Scene //        GraphPinScene
{

    private LocalInstanceMonitor monitor;
    private Map<ActivityComponent, Widget> mActToWidget = new HashMap<ActivityComponent, Widget>();

    public MonitorScene(LocalInstanceMonitor monitor) {
        this.monitor = monitor;

        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 4));

        mActToWidget.put(monitor.getInstance(), this);

        setUp();
    }

    private void setUp() {
        for (ActivityComponent activityComponent : monitor.getExecution()) {

            if (mActToWidget.containsKey(activityComponent)) {
                System.out.println("Activity yet present on Scene " + activityComponent);
                continue;
            }

            ActivityWidget widget = WidgetFactory.makeWidget(this, activityComponent, null);



            if (widget != null) {

                ActivityComponent parentActivity = activityComponent.getParentComponent();
                Widget parentWidget = mActToWidget.get(parentActivity);

                if (parentWidget != null) {

                    parentWidget.addChild(widget);
                    System.out.println("===============================>> Activity " + activityComponent + " with parent " + parentActivity +  " ADDED!!");

                } else {
                    System.out.println("===============================>> Activity " + activityComponent + " with parent " + parentActivity +  " without parent Widget");
                }

                mActToWidget.put(activityComponent, widget);
            } else {
                System.out.println("===============================>> NO WIDGET For Activity " + activityComponent);
            }

        }
    }

//    @Override
//    protected Widget attachNodeWidget(Object node) {
//        return null;
//    }
//
//    @Override
//    protected Widget attachEdgeWidget(Object edge) {
//        return null;
//    }
//
//    @Override
//    protected Widget attachPinWidget(Object node, Object pin) {
//        return null;
//    }
//
//    @Override
//    protected void attachEdgeSourceAnchor(Object edge, Object oldSourcePin, Object sourcePin) {
//    }
//
//    @Override
//    protected void attachEdgeTargetAnchor(Object edge, Object oldTargetPin, Object targetPin) {
//    }
}
