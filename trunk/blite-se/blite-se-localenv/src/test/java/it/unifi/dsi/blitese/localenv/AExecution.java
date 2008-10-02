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

package it.unifi.dsi.blitese.localenv;

import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BliteParser;
import it.unifi.dsi.blitese.parser.ParseException;
import java.io.File;
import java.io.FileInputStream;
import junit.framework.TestCase;

/**
 *
 * @author panks
 */
public abstract class AExecution extends TestCase {


    public AExecution(String testName) {
        super(testName);
    }


    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    /**
     * 
     * @throws java.lang.Exception
     */
    public void testExecute() throws Exception {
        
        File file1 = getBliteTestFile(getFileName());
        FileInputStream inputStream = new FileInputStream(file1);
        
        
        BliteParser.init(inputStream);
       
        
        LocalEnvironment environment = new LocalEnvironment();
        
        try {
            BLTDEFCompilationUnit cu = (BLTDEFCompilationUnit) BliteParser.parse();
            cu.setResource(file1.toURI().toURL());
            
            environment.addCompilationUnit(cu);
            
            Thread.sleep(5000);
            
        } catch (ParseException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    
    abstract String getFileName();
    
    /**
     * return File object with test data dir path appended.
     * @param relFilePath relative path w.r.t. ../blite-se-defmodel/src/test/data
     * @return the File object
     */
    static File getBliteTestFile(String relFilePath) {

        String basedir = System.getProperty("basedir");
        String bliteFilePath = "src/test/data/" + relFilePath; // relative to basedir;

        File bliteFile = new File(basedir, bliteFilePath);
        return bliteFile;
    }

    
}
