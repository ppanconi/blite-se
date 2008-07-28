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

import java.io.InputStream;

/**
 *
 * @author panks
 */
public abstract class AbstractBliteParser {
    
    static public AbstractBliteParser provideInstance(InputStream stream) {
        BliteParser bliteParser = new BliteParser(stream);
        return bliteParser;
    }
    
    abstract public BltDefBaseNode parse() throws ParseException;

}
