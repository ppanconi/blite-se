package it.unifi.dsi.blitese.engine.runtime;

import it.unifi.dsi.blitese.parser.BltDefBaseNode;

/**
 * The base unit of runtime execution of a Runtime Process Instance.
 * The method <tt>doActivity</tt> it the key of the execution model.
 *
 * @author panks
 */
public interface ActivityComponent {

    public boolean doActivity();

    public ActivityComponent getParentComponent();

    public BltDefBaseNode getBltDefNode();
}
