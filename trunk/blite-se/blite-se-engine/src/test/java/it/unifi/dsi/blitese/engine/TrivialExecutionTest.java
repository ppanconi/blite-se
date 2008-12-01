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

package it.unifi.dsi.blitese.engine;

import it.unifi.dsi.blitese.engine.runtime.Engine;
import it.unifi.dsi.blitese.engine.runtime.imp.EngineImp;
import it.unifi.dsi.blitese.parser.BLTDEFCompilationUnit;
import it.unifi.dsi.blitese.parser.BLTDEFDeployment;
import it.unifi.dsi.blitese.parser.BliteParser;
import it.unifi.dsi.blitese.parser.ParseException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author panks
 */
public class TrivialExecutionTest extends TestCase {
    
    Engine engine = new EngineImp();
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TrivialExecutionTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TrivialExecutionTest.class);
    }

    /**
     * test parsing of trivial blite definition locate at
     * <module_dir>/src/test/data/trivial.blt
     * 
     * @throws java.lang.Exception
     */
    public void testExecute() throws Exception {
        
        InputStream inputStream = new FileInputStream(getBliteTestFile("trivial.blt"));
        BliteParser.init(inputStream);
        
        try {
            BLTDEFCompilationUnit bliteTree = (BLTDEFCompilationUnit) BliteParser.parse();
            BLTDEFDeployment deployment = (BLTDEFDeployment) bliteTree.getDeployments().get(2);
            
//            BliteDeploymentDefinition def = new BliteDeploymentDefinitionImp(deployment, "TrivialProcess");
//            engine.addProcessDefinition(def, null, null);
//            
            
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
        String bliteFilePath = "../blite-se-defmodel/src/test/data/" + relFilePath; // relative to basedir;

        File bliteFile = new File(basedir, bliteFilePath);
        return bliteFile;
    }

}
