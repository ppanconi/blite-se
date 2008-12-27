/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.monitor;

import it.unifi.dsi.blide.monitor.widgets.ActivityWidget;
import it.unifi.dsi.blide.monitor.widgets.InstanceWidget;
import it.unifi.dsi.blide.monitor.widgets.WidgetFactory;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.localenv.LocalInstanceMonitor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author panks
 */
public class MonitorScene extends Scene //        GraphPinScene
    implements ChangeListener

{
    

    private LocalInstanceMonitor monitor;
    private Map<ActivityComponent, ActivityWidget> mActToWidget = new HashMap<ActivityComponent, ActivityWidget>();

    public MonitorScene(LocalInstanceMonitor monitor) {
        this.monitor = monitor;

        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 15));

        getActions().addAction(ActionFactory.createZoomAction ());
//        getActions().addAction (ActionFactory.createPanAction ());
        getActions().addAction(ActionFactory.createWheelPanAction());
        getActions().addAction(new RefrashAction());
        
        setUp(monitor);
        validate();

    }



    private void setUp(LocalInstanceMonitor monitor) {

        Widget w = new Widget(this);
        w.setMinimumSize(new Dimension(20, 10));
        addChild(w);

        ProcessInstance instance = monitor.getInstance();
        InstanceWidget instanceWidget = new InstanceWidget(this, instance);

        addChild(instanceWidget);
        mActToWidget.put(instance, instanceWidget);
        
                 //BorderFactory.createSwingBorder(this, new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Composite")), BORDER_SHADOW_NORMAL));
        for (ActivityComponent activityComponent : monitor.getExecution()) {

            ActivityWidget widget = mActToWidget.get(activityComponent);
            if (widget != null) {
//                System.out.println("Activity yet present on Scene " + activityComponent);

                if (widget.upadte()) widget.repaint();

                continue;
            }

            widget = WidgetFactory.makeWidget(this, activityComponent, null);

            if (widget != null) {

                ActivityComponent parentActivity = activityComponent.getParentComponent();
                ActivityWidget parentWidget = mActToWidget.get(parentActivity);

                if (parentWidget != null) {

                    parentWidget.add(widget);
//                    System.out.println("===============================>> Activity " + activityComponent + " with parent " + parentActivity +  " ADDED!!");

                } else {
//                    System.out.println("===============================>> Activity " + activityComponent + " with parent " + parentActivity +  " without parent Widget");
                }

                mActToWidget.put(activityComponent, widget);
            } else {
//                System.out.println("===============================>> NO WIDGET For Activity " + activityComponent);
            }

        }

        instanceWidget.close();
    }

    private void refresh() {
        System.out.println("===============================>> XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        mActToWidget.clear();
        removeChildren();
        validate();
        setUp(monitor);
        validate();
    }

    public void stateChanged(ChangeEvent e) {
        refresh();
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

    private class RefrashAction extends WidgetAction.Adapter {

        @Override
        public State mousePressed (Widget widget, WidgetMouseEvent event) {
            refresh();
            return State.CONSUMED;
        }
    }

}
