/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.run.imp;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.util.actions.Presenter;

public final class SingleStepAction implements ActionListener, Presenter.Toolbar {

    private Component mPreseter = new SingleStepPanel();

    public void actionPerformed(ActionEvent e) {
        // NOTE NEVER USED
    }

    @Override
    public Component getToolbarPresenter() {
        return mPreseter;
    }
}
