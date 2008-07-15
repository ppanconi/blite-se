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

package it.unifi.dsi.blitese.jbi;

import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

/**
 *
 * @author panks
 */
public class BliteseInstallerConfiguration extends StandardMBean 
        implements BliteseInstallerConfigurationMBean {

    public BliteseInstallerConfiguration() throws NotCompliantMBeanException {
        super(BliteseInstallerConfiguration.class);
    }

    
}
