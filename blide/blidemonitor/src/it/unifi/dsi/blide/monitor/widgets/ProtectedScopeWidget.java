/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import java.awt.Color;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author panks
 */
public class ProtectedScopeWidget extends ScopeWidget {

    public ProtectedScopeWidget(Scene scene, ActivityComponent activity) {
        super(scene, activity);
    }

    @Override
    protected Color provideBorderColor() {
        return Color.RED;
    }

    
}
