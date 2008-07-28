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

package it.unifi.dsi.blitese.parser;

import java.io.File;
import java.io.FileInputStream;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This is a trivial test case. It try to parse a minimal
 * blite program. 
 * 
 * @author panks
 */
public class TrivialParseTest extends TestCase {

    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TrivialParseTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TrivialParseTest.class);
    }

    /**
     * test parsing of trivial blite definition locate at
     * <module_dir>/src/test/data/trivial.blt
     * 
     * @throws java.lang.Exception
     */
    public void testParse() throws Exception {
        
        AbstractBliteParser parser = 
                AbstractBliteParser.provideInstance(new FileInputStream(getBliteTestFile("trivial.blt")));
        
        try {
            SimpleNode bliteTree = (SimpleNode) parser.parse();
            bliteTree.dump("+");
            
        } catch (ParseException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    
    
    /**
     * return File object with test data dir path appended.
     * @param relFilePath relative path w.r.t. src/test/data
     * @return the File object
     */
    static File getBliteTestFile(String relFilePath) {

        String basedir = System.getProperty("basedir");
        String bliteFilePath = "src/test/data/" + relFilePath; // relative to basedir;

        File bliteFile = new File(basedir, bliteFilePath);
        return bliteFile;
    }
}
