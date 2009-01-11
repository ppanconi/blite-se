/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.engine.runtime.activities.imp;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.imp.ExpressionInterpreter;
import it.unifi.dsi.blitese.parser.BLTDEFConditionalActivity;
import it.unifi.dsi.blitese.parser.BLTDEFExpression;

/**
 *
 * @author panks
 */
public class ConditionalActivity extends ActivityComponentBase {

    private BLTDEFConditionalActivity condDef;
    private boolean tested = false;
    private Boolean testValue = Boolean.FALSE;

    public Boolean getTestValue() {
        return testValue;
    }

    public boolean isTested() {
        return tested;
    }

    @Override
    public void init() {
        super.init();
        condDef = (BLTDEFConditionalActivity) getBltDefNode();
    }


    public boolean doActivity() {
        //we check if we had the input to terminate or test yet executed
        if (!getContext().isInAFaultedBranch() && !tested) {

            BLTDEFExpression expr = (BLTDEFExpression) condDef.jjtGetChild(0);

            Object exprValue = ExpressionInterpreter.evaluate(getContext(), expr);

            currentChildIndex = 2; //false test case

            if (exprValue instanceof Boolean) {
                testValue = (Boolean) exprValue;
                if (testValue) {
                    currentChildIndex = 1; //true test case
                }
            }
            // move on the actual activity state
            tested = true;

            ActivityComponent _nextAct = nextChildActivity(getContext(), getExecutor());

            getExecutor().setCurrentActivity(_nextAct);
            return true;
        }

        flowParent();
        return true;

    }

}
