/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor;

import it.unifi.dsi.blitese.engine.runtime.InstanceMonitor;
import org.netbeans.api.visual.graph.GraphPinScene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author panks
 */
public class MonitorScene extends GraphPinScene {

    private InstanceMonitor monitor;

    public MonitorScene(InstanceMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    protected Widget attachNodeWidget(Object node) {
        return null;
    }

    @Override
    protected Widget attachEdgeWidget(Object edge) {
        return null;
    }

    @Override
    protected Widget attachPinWidget(Object node, Object pin) {
        return null;
    }

    @Override
    protected void attachEdgeSourceAnchor(Object edge, Object oldSourcePin, Object sourcePin) {
    }

    @Override
    protected void attachEdgeTargetAnchor(Object edge, Object oldTargetPin, Object targetPin) {
    }


}
