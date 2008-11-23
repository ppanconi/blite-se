/*
 *  DO NOT EDIT
 * 
 *  The contents of this file are subject to the terms
 *  of the GNU General Public License v3
 *  You may not use this file except
 *  in compliance with the License.
 * 
 *  You can obtain a copy of the license at
 *  http://www.gnu.org/licenses/gpl.html
 *  See the License for the specific language governing
 *  permissions and limitations under the License.
 * 
 */

package it.unifi.dsi.blitese.localenv.gui;

import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BliteParser;
import it.unifi.dsi.blitese.parser.ParseException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

/**
 * Probably temporary class to manage basic operation on sources.

 * This a facade pattern to postpone necessary design choices about 
 * organization model of sources, compilation strategies, and advanced 
 * gui components for editing sources.
 *
 * @author panks
 */
public class SourcesFacadeManager {
    
    
    private Map<JComponent, File> mCompToFile = new HashMap<JComponent, File>();
    private Map<File, JComponent> mFileToComp = new HashMap<File, JComponent>();
    private Map<JComponent, BLTDEFCompilationUnit> mCompToCompUnit = new HashMap<JComponent, BLTDEFCompilationUnit>();
    
    
    public JComponent addSource(File source) throws IOException {
        JScrollPane jScrollPane = new JScrollPane();
        
        jScrollPane.setViewportView(putText(source));
        
        mCompToFile.put(jScrollPane, source);
        mFileToComp.put(source, jScrollPane);
        
        return jScrollPane;
   
    }
    
//    private BliteParser parser = null;
                
        
    public BLTDEFCompilationUnit buildSource(JComponent component) throws FileNotFoundException, ParseException, MalformedURLException {
        
        File file = mCompToFile.get(component);
        
        FileInputStream inputStream = new FileInputStream(file);
        
//        if (parser == null)
//            parser = (BliteParser) AbstractBliteParser.provideInstance(inputStream);
//        else {
//            parser.ReInit(inputStream);
//            AbstractBliteParser.cleanTables();
//        }
        BliteParser.init(inputStream);
        
        BLTDEFCompilationUnit compilationUnit = (BLTDEFCompilationUnit) BliteParser.parse();
        compilationUnit.setResource(file.toURI().toURL());
        
        mCompToCompUnit.put(component, compilationUnit);
        
        return  compilationUnit;
        
    }
    
    public File saveSource(JComponent component) throws IOException {
        
        File file = mCompToFile.get(component);
        String source = getText(file);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(source);
            fw.flush(); 
        } finally {
            if (fw != null) fw.close();
        }
        
        return file;
    }
    
    
    private Map<File, JTextComponent> mFileToText = new HashMap<File, JTextComponent>();
    
    private JTextComponent putText(File file) throws IOException {
        
        JEditorPane editorPane = new JEditorPane();
        mFileToText.put(file, editorPane);
        editorPane.setContentType("text/java");
        editorPane.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        editorPane.read(new FileReader(file), file);
        
//        JTextArea jTextArea = new JTextArea();
//        jTextArea.read(new FileReader(file), file);
//        mFileToText.put(file, jTextArea);
//        
        return editorPane;
    }
    
    private String getText(File file) {
        return mFileToText.get(file).getText();
    }
    
    public Collection<BLTDEFCompilationUnit> proviedeCompiledUnits() {
        return mCompToCompUnit.values();
    }
}
