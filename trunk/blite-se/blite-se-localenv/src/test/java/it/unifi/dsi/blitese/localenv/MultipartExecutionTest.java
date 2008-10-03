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

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 * @author panks
 */
public class MultipartExecutionTest extends AExecution {

    public MultipartExecutionTest(String testName) {
        super(testName);
    }
    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MultipartExecutionTest.class);
    }

    @Override
    String getFileName() {
        return "multipart.blt";
    }

    
}
