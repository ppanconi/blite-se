/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.lang;

import it.unifi.dsi.blitese.parser.ParseException;
import java.io.IOException;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

public final class CompileAction extends CookieAction {

    protected void performAction(Node[] activatedNodes) {
        BliteDataObject bliteDataObject = activatedNodes[0].getLookup().lookup(BliteDataObject.class);
//        InputStream stream = null;
        InputOutput output = IOProvider.getDefault().getIO(bliteDataObject.getName(), false);

        try {
            output.getOut().reset();
            output.getOut().write("Compiling file '" + bliteDataObject.getName() + "'... \n");
//            stream = bliteDataObject.getPrimaryFile().getInputStream();

//            BliteParser.init(stream);
//            BliteParser.parse();

            BliteDefModelProvider modelProvider =
                    bliteDataObject.getNodeDelegate().getLookup().lookup(BliteDefModelProvider.class);

            modelProvider.compile();

            output.getOut().write("----------------------------------------\n");
            output.getOut().write("COMPILE SUCCESSFUL\n");
            output.getOut().write("----------------------------------------\n");

            StatusDisplayer.getDefault().setStatusText("File '" + bliteDataObject.getName() + "' compiled successfully");

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ParseException ex) {

            String errMsg = ex.getMessage();
            try {
                output.getErr().println("Compilation Error: " + errMsg, new BliteOutputListener(bliteDataObject, errMsg), true);
            } catch (IOException ex1) {
                Exceptions.printStackTrace(ex1);
            }
        }
//        finally {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException ex) {
//                    Exceptions.printStackTrace(ex);
//                }
//            }
//        }
    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(CompileAction.class, "CTL_CompileAction");
    }

    protected Class[] cookieClasses() {
        return new Class[]{BliteDataObject.class};
    }

    @Override
    protected String iconResource() {
        return "it/unifi/dsi/blide/lang/build.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return true;
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        boolean enab = super.enable(activatedNodes);

        if (enab) {

            BliteDefModelProvider mp = activatedNodes[0].getLookup().lookup(BliteDefModelProvider.class);

            if (mp.getDefinitionModel() != null) {
                enab = false;
            }
        }

        return enab;
    }
}

