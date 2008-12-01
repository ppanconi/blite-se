/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.lang;

import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.ParseException;

/**
 *
 * @author panks
 */
public interface BliteDefModelProvider {

    public void compile() throws ParseException;

    public BLTDEFCompilationUnit getDefinitionModel();
}
