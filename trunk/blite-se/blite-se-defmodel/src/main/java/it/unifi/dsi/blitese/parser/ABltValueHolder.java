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

/**
 *
 * @author panks
 */
public abstract class ABltValueHolder extends SimpleNode {
    
    public ABltValueHolder(BliteParser p, int i) {
        super(p, i);
    }

    public ABltValueHolder(int i) {
        super(i);
    }
    
    private Object literalValue;
    private String varableName;

    public Object getLiteralValue() {
        return literalValue;
    }

    public void setLiteralValue(Object literalValue) {
        this.literalValue = literalValue;
    }

    public String getVarableName() {
        return varableName;
    }

    public void setVarableName(String varableName) {
        this.varableName = varableName;
    }
    
    
    public boolean isLeteral() {
        if (provideLiteralValue() == null) return false;
        else return true;
    }
    
    public Object provideLiteralValue() {
        return getLiteralValue();
    }

    public String provideVariableName() {
        return getVarableName();
    }
}
