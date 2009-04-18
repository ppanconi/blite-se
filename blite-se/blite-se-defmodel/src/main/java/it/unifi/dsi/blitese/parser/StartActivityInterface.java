/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blitese.parser;

import java.util.List;

/**
 *
 * @author panks
 */
public interface StartActivityInterface {

    public List<String> provideStartPorts();

    public void addStartPorts(List<String> ports);

    public void addStartPort(String port);

}
