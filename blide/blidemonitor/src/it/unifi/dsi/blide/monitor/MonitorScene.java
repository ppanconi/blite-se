/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.monitor;

import it.unifi.dsi.blide.monitor.widgets.ActivityWidget;
import it.unifi.dsi.blide.monitor.widgets.ExchangeFlowWidget;
import it.unifi.dsi.blide.monitor.widgets.ExchangeWidget;
import it.unifi.dsi.blide.monitor.widgets.InstanceWidget;
import it.unifi.dsi.blide.monitor.widgets.ProtectedScopeWidget;
import it.unifi.dsi.blide.monitor.widgets.ScopeWidget;
import it.unifi.dsi.blide.monitor.widgets.WidgetFactory;
import it.unifi.dsi.blide.run.imp.InstanceNode;
import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.imp.ABaseContext;
import it.unifi.dsi.blitese.engine.runtime.imp.ProtecedScope;
import it.unifi.dsi.blitese.localenv.LocalInstanceMonitor;
import it.unifi.dsi.blitese.parser.BltDefBaseNode;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author panks
 */
public class MonitorScene extends Scene //        GraphPinScene
        implements ChangeListener {

    private LayerWidget mainLayer;
    private LayerWidget connectionLayer;

    public LayerWidget getConnectionLayer() {
        return connectionLayer;
    }

    public LayerWidget getMainLayer() {
        return mainLayer;
    }

    private List<LocalInstanceMonitor> monitors = new ArrayList<LocalInstanceMonitor>();
    private Map<ActivityComponent, ActivityWidget> mActToWidget = new HashMap<ActivityComponent, ActivityWidget>();

    private ExchangeFlowWidget exchangeFlow;
    private Map<Object, ExchangeWidget> mExIdToWidget = new HashMap<Object, ExchangeWidget>();

    public MonitorScene(LocalInstanceMonitor monitor) {
        monitors.add(monitor);

        mainLayer = new LayerWidget(this);
        addChild (mainLayer);
        connectionLayer = new LayerWidget(this);
        addChild (connectionLayer);


        mainLayer.setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 15));

        getActions().addAction(ActionFactory.createAcceptAction(new InstanceAcceptProvider()));
        getActions().addAction(ActionFactory.createZoomAction());
//        getActions().addAction (ActionFactory.createPanAction ());
        getActions().addAction(ActionFactory.createWheelPanAction());
        getActions().addAction(new RefrashAction());

        addInstance(monitor);

    }

    private void addExchangeFlow(ExchangeFlowWidget efw) {
        Widget w = new Widget(this);
        w.setMinimumSize(new Dimension(20, 10));
        mainLayer.addChild(w);

        mainLayer.addChild(efw);
    }

    private void addInstance(LocalInstanceMonitor monitor) {

        Widget w = new Widget(this);
        w.setMinimumSize(new Dimension(20, 10));
        mainLayer.addChild(w);

        ProcessInstance instance = monitor.getInstance();
        InstanceWidget instanceWidget = new InstanceWidget(this, instance);

        mainLayer.addChild(instanceWidget);
        mActToWidget.put(instance, instanceWidget);

        //BorderFactory.createSwingBorder(this, new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), "Composite")), BORDER_SHADOW_NORMAL));

        List<ActivityComponent> acts = monitor.getExecution();
        synchronized (acts) {
            for (ActivityComponent activityComponent : acts) {

                //check if it's invisible activity
                BltDefBaseNode def = activityComponent.getBltDefNode();
                if (def != null && !def.isVisible()) continue;

                ActivityWidget widget = mActToWidget.get(activityComponent);
                if (widget != null) {
    //                System.out.println("Activity yet present on Scene " + activityComponent);

//                    if (widget.upadte()) {
//                        widget.repaint();
//                    }

                    //IT YET PRESENT
                    continue;
                }

                widget = WidgetFactory.makeWidget(this, activityComponent, null);

                if (widget != null) {

                    if (activityComponent instanceof ProtecedScope) {
                        //if we hava a ProtecedScope we add it to the launcherContext
                        //and not to the natural partent Context
                        ProtecedScope protecedScope = (ProtecedScope) activityComponent;
                        ABaseContext laucherContext = protecedScope.getLauncherContext();
                        
                        ScopeWidget parentWidget = (ScopeWidget) mActToWidget.get(laucherContext);
                        if (parentWidget != null) {
                            parentWidget.addProteced((ProtectedScopeWidget) widget);
                        }

                    } else {

                        ActivityComponent parentActivity = activityComponent.getParentComponent();
                        ActivityWidget parentWidget = mActToWidget.get(parentActivity);

                        if (parentWidget != null) {
                            parentWidget.add(widget);
        //                    System.out.println("===============================>> Activity " + activityComponent + " with parent " + parentActivity +  " ADDED!!");
                        }
                    }

                    mActToWidget.put(activityComponent, widget);

                } else {
    //                System.out.println("===============================>> NO WIDGET For Activity " + activityComponent);
                }

            }
        }

        instanceWidget.close();
    }

    public void clear() {
        mActToWidget.clear();
        mExIdToWidget.clear();
        exchangeFlow = null;
        mainLayer.removeChildren();
        connectionLayer.removeChildren();
    }

    private void refresh() {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                repaint();
                clear();

                for (LocalInstanceMonitor monitor : monitors) {
                    addInstance(monitor);
                }
                
                validate();

            }
        });
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
        public State mousePressed(Widget widget, WidgetMouseEvent event) {
            refresh();
            return State.CONSUMED;
        }
    }

    private class InstanceAcceptProvider implements AcceptProvider {

        public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {

            if (transferable.isDataFlavorSupported(InstanceNode.INSTANCE_MONITOR_FLAVOR)) {
                try {
                    LocalInstanceMonitor monitor =
                            (LocalInstanceMonitor) transferable.getTransferData(InstanceNode.INSTANCE_MONITOR_FLAVOR);

                    JComponent view = getView();
                    Graphics2D g2 = (Graphics2D) view.getGraphics();
                    Rectangle visRect = view.getVisibleRect();
                    view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);
                    g2.drawImage(ImageUtilities.loadImage("it/unifi/dsi/blide/monitor/widgets/resources/instance-dnd.png"),
                                AffineTransform.getTranslateInstance(point.getLocation().getX(),
                                point.getLocation().getY()),
                                null);
                } catch (UnsupportedFlavorException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }

                return ConnectorState.ACCEPT;
            } else {
                return ConnectorState.REJECT;
            }
        }

        public void accept(Widget widget, Point point, Transferable transferable) {

            LocalInstanceMonitor monitor = null;
            try {
                monitor = (LocalInstanceMonitor) transferable.getTransferData(InstanceNode.INSTANCE_MONITOR_FLAVOR);
            } catch (UnsupportedFlavorException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }


            if (!monitors.contains(monitor)) {
                monitors.add(monitor);
                refresh();
            }
        }

    }

    public Widget addExchange(MessageContainer messageContainer) {
        if (exchangeFlow == null) {
            exchangeFlow = new ExchangeFlowWidget(this);
            addExchangeFlow(exchangeFlow);
        }

        Object meId = messageContainer.getId();

        ExchangeWidget ew = mExIdToWidget.get(meId);

        if (ew == null) {
            ew = new ExchangeWidget(this, meId);
            exchangeFlow.addChild(ew);
            mExIdToWidget.put(meId, ew);
        }

        return ew;
    }

//    private static ProcessInstance instanceFromTransferable(Transferable transferable) {
//        transferable.getTransferData(DataFlavor.javaJVMLocalObjectMimeType);
//    }
}
