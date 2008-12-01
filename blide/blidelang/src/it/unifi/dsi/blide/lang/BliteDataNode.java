/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unifi.dsi.blide.lang;

import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BliteParser;
import it.unifi.dsi.blitese.parser.ParseException;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;
import org.openide.util.Lookup;
import org.openide.util.lookup.ProxyLookup;
/**
 *
 * @author panks
 */
public class BliteDataNode extends DataNode {

    private BliteDefModelProviderImp modelProvider;
    public BliteDataNode(BliteDataObject obj, Lookup  lookup) {
        super(obj, Children.LEAF,  new ProxyLookup(lookup, Lookups.fixed(new Object[]{new BliteDefModelProviderImp(obj)})));
        modelProvider = getLookup().lookup(BliteDefModelProviderImp.class);
        modelProvider.setDataNode(this);

        FileObject file = obj.getPrimaryFile();
        file.addFileChangeListener(FileUtil.weakFileChangeListener(modelProvider, file));
    }

    private static final String NEEDS_COMPILE = "it/unifi/dsi/blide/lang/needs-compile.png";

    @Override
    public Image getIcon (int type) {
        Image original = super.getIcon (type);

        if (modelProvider.getDefinitionModel() == null) {

            Image errorBadge = ImageUtilities.loadImage (NEEDS_COMPILE);
            return ImageUtilities.mergeImages(original, errorBadge, 12, 0);
        } else 
            return original;
    }


    /*------------------------------------------------------
     *
     */
    static private class BliteDefModelProviderImp extends FileChangeAdapter implements
            BliteDefModelProvider {

        private BliteDataObject dataObject;
        private BliteDataNode dataNode;
        //cache for compilation
        private BLTDEFCompilationUnit definitionModel;

        public BliteDefModelProviderImp(BliteDataObject dataObject) {
            this.dataObject = dataObject;
        }

        public void setDataNode(BliteDataNode dataNode) {
            this.dataNode = dataNode;
        }

        public void reset() {
            definitionModel = null;
            dataNode.fireIconChange();
        }

        public BLTDEFCompilationUnit getDefinitionModel() {
            return definitionModel;
        }

        public void compile() throws ParseException {

            if (EventQueue.isDispatchThread()) {
                throw new IllegalStateException("Don't call on event thread");
            }

            InputStream stream = null;
            try {

                stream = dataObject.getPrimaryFile().getInputStream();
                BliteParser.init(stream);
                //parse and cache the result
                definitionModel = (BLTDEFCompilationUnit) BliteParser.parse();
                dataNode.fireIconChange();
                

            } catch (FileNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

            }
        }

        @Override
        public void fileChanged(FileEvent fe) {
            super.fileChanged(fe);
            reset();
        }


    }
}