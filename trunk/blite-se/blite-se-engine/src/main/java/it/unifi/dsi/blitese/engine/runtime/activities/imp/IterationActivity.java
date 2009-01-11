/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.engine.runtime.activities.imp;

import it.unifi.dsi.blitese.engine.runtime.ActivityComponent;
import it.unifi.dsi.blitese.engine.runtime.imp.ExpressionInterpreter;
import it.unifi.dsi.blitese.parser.BLTDEFExpression;
import it.unifi.dsi.blitese.parser.BLTDEFIterationActivity;

/**
 *
 * @author panks
 */
public class IterationActivity extends ActivityComponentBase {

    private BLTDEFIterationActivity iterDef;

    @Override
    public void init() {
        super.init();
        iterDef = (BLTDEFIterationActivity) getBltDefNode();
    }



    public boolean doActivity() {
        
        //we check if we had the input to terminate or test yet executed
        if (!getContext().isInAFaultedBranch()) {

            BLTDEFExpression expr = (BLTDEFExpression) iterDef.jjtGetChild(0);

            Object exprValue = ExpressionInterpreter.evaluate(getContext(), expr);


            if (exprValue instanceof Boolean) {
                Boolean boolValue = (Boolean) exprValue;
                if (Boolean.TRUE.compareTo(boolValue) == 0) {
                    
                    currentChildIndex = 1; //true test case
                    ActivityComponent _nextAct = nextChildActivity(getContext(), getExecutor());

                    getExecutor().setCurrentActivity(_nextAct);
                    return true;
                }
            }
            
        }

        flowParent();
        return true;
    }

}
