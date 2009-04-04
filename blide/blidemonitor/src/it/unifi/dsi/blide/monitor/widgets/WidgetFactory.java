/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.monitor.widgets;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.FlowOwner;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.AssignActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ConditionalActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.EmptyActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.FlowActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.InvokeActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.PickActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ReceiveActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ScopeActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.SequenceActivity;
import it.unifi.dsi.blitese.engine.runtime.activities.imp.ThrowActivity;
import it.unifi.dsi.blitese.engine.runtime.imp.ProtecedScope;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author panks
 */
public class WidgetFactory {

    public static ActivityWidget makeWidget(Scene scene, ActivityComponent activityComponent, FlowOwner flowOwner) {

        ActivityWidget widget = null;

        if (activityComponent instanceof AssignActivity) {
            AssignActivity assignActivity = (AssignActivity) activityComponent;

            widget = new AssignWidget(scene, activityComponent);

        } else if (activityComponent instanceof EmptyActivity) {
            EmptyActivity emptyActivity = (EmptyActivity) activityComponent;

            widget = new EmptyWidget(scene, activityComponent);

        } else if (activityComponent instanceof FlowActivity) {
            FlowActivity flowActivity = (FlowActivity) activityComponent;

            widget = new FlowWidget(scene, activityComponent);

        } else if (activityComponent instanceof InvokeActivity) {
            InvokeActivity invokeActivity = (InvokeActivity) activityComponent;

            widget = new InvokeWidget(scene, activityComponent);

        } else if (activityComponent instanceof ReceiveActivity) {
            ReceiveActivity receiveActivity = (ReceiveActivity) activityComponent;

            widget = new ReceiveWidget(scene, activityComponent);

        } else if (activityComponent instanceof ProtecedScope) {
            ProtecedScope scopeActivity = (ProtecedScope) activityComponent;

            widget = new ProtectedScopeWidget(scene, activityComponent);

        } else if (activityComponent instanceof ScopeActivity) {
            ScopeActivity scopeActivity = (ScopeActivity) activityComponent;

            widget = new ScopeWidget(scene, activityComponent);

        } else if (activityComponent instanceof SequenceActivity) {
            SequenceActivity sequenceActivity = (SequenceActivity) activityComponent;

            widget = new SequenceWidget(scene, activityComponent);

        } else if (activityComponent instanceof ThrowActivity) {
            ThrowActivity throwActivity = (ThrowActivity) activityComponent;

            widget = new ThrowWidget(scene, activityComponent);

        } else if (activityComponent instanceof ConditionalActivity) {
            ConditionalActivity conditionalActivity  = (ConditionalActivity) activityComponent;

            widget = new ConditionalWidget(scene, conditionalActivity);

        } else if (activityComponent instanceof PickActivity) {
            PickActivity pickActivity  = (PickActivity) activityComponent;

            widget = new PickWidget(scene, pickActivity);
        }

        return widget;
    }
}
