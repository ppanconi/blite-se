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

import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.imp.EngineImp;
import it.unifi.dsi.blitese.parser.AbstractBliteParser;
import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BLTDEFDeployment;
import it.unifi.dsi.blitese.parser.ParseException;
import java.io.File;
import java.io.FileInputStream;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author panks
 */
public class Execution1Test extends TestCase {
    
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Execution1Test(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(Execution1Test.class);
    }

    /**
     * 
     * @throws java.lang.Exception
     */
    public void testExecute() throws Exception {
        
        File file1 = getBliteTestFile("trivial.blt");
        AbstractBliteParser parser = 
                AbstractBliteParser.provideInstance(new FileInputStream(file1));
        
        LocalEnvironment environment = new LocalEnvironment();
        
        try {
            BLTDEFCompilationUnit cu = (BLTDEFCompilationUnit) parser.parse();
            cu.setResource(file1.toURI().toURL());
            
            
            environment.addCompilationUnit(cu);
            
            Thread.sleep(10000);
            
        } catch (ParseException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    
    
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
