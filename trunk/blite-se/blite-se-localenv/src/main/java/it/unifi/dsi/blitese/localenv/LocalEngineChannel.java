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
import it.unifi.dsi.blitese.engine.runtime.EngineChannel;
import it.unifi.dsi.blitese.engine.runtime.MessageContainer;
import it.unifi.dsi.blitese.engine.runtime.ProcessInstance;
import it.unifi.dsi.blitese.engine.runtime.ServiceIdentifier;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panks
 */
public class LocalEngineChannel implements EngineChannel {

    public static final int THREAD_NUM = 4;

    private static final Logger LOGGER = Logger.getLogger(LocalEngineChannel.class.getName());
    final private LocalEnvironment environment;
    private ExecutorService exService = Executors.newFixedThreadPool(THREAD_NUM);
    private boolean open = true;

    public boolean isOpen() {
        return open;
    }

    public void open() {
        this.open = true;
    }
    
    public void close() {
        this.open = false;
    }
    
    private long messageExchangeCounter = 0L;

    synchronized private Long nextMEId() {
        return new Long(messageExchangeCounter++);
    }
    private ConcurrentMap<Long, EnginesConnection> connections = new ConcurrentHashMap<Long, EnginesConnection>();
    private BlockingQueue<MessageEnvelop> mMedia = new LinkedBlockingDeque<MessageEnvelop>();

    public LocalEngineChannel(LocalEnvironment env) {
        this.environment = env;

        for (int i = 0; i < THREAD_NUM; i++) {

            exService.execute(new Runnable() {

                public void run() {
               
                    while (open) {
                        try {

                            MessageEnvelop envelop = mMedia.poll(300, TimeUnit.MILLISECONDS);


                            if (envelop != null) {

                                Long meId = envelop.meId;
                                EnginesConnection connection = connections.get(meId);

                                MessageContainer mc = envelop.messageContainer;
                                mc.setId(meId);

                                Engine destEngine = null;

                                if (mc.getType() == MessageContainer.Type.MESSAGE ||
                                        mc.getType() == MessageContainer.Type.FAULT) {

                                    if (environment.getStepper().isEnabled()) {
                                        environment.getStepper().step();
                                    }

                                    LOGGER.info("Removed MESSAGE form Channel: " + envelop);
                                    destEngine = connection.provider;
                                    destEngine.processRequest(envelop.serviceId, envelop.operation, mc);

                                } else {

                                    LOGGER.info("Removed STATUS form Channel: " + envelop);
                                    destEngine = connection.consumer;
                                    destEngine.processExchange(mc);

                                }
                            }

                        } catch (InterruptedException ex) {
                            Logger.getLogger(LocalEngineChannel.class.getName()).log(Level.SEVERE, null, ex);
                            throw new RuntimeException(ex.getMessage(), ex);
                        }
                    }

                }
            });
        }
    }

    public Object createExchange(ServiceIdentifier serviceId, String operation, ProcessInstance instance) {
        Long meId = nextMEId();

        Engine consumer = instance.getManager().getEngine();
        Engine provider = environment.provideServiceEngine(serviceId);

        connections.put(meId, new EnginesConnection(meId, consumer, provider, serviceId, operation));
        
        LOGGER.info("Created MessageExchange " + meId);
        
        return meId;
    }

    public void sendIntoExchange(Object meId, MessageContainer mc) {

        EnginesConnection con = connections.get(meId);

        MessageEnvelop envelop =
                new MessageEnvelop((Long) meId, con.serviceId, con.operation, mc);
        try {

            mMedia.put(envelop);
        } catch (InterruptedException ex) {
            Logger.getLogger(LocalEngineChannel.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
        
        LOGGER.info("Pulled Envelop in Channel " + envelop);
        
    }

    public void closeExchange(Object messageExchangeId) {
        connections.remove(messageExchangeId);
    }

    private static class EnginesConnection {

        Long meId;
        Engine consumer;
        Engine provider;
        ServiceIdentifier serviceId;
        String operation;

        public EnginesConnection(Long meId, Engine consumer, Engine provider, ServiceIdentifier serviceId, String operation) {
            this.meId = meId;
            this.consumer = consumer;
            this.provider = provider;
            this.serviceId = serviceId;
            this.operation = operation;
        }
    }

    private static class MessageEnvelop {

        Long meId;
        ServiceIdentifier serviceId;
        String operation;
        MessageContainer messageContainer;

        public MessageEnvelop(Long meId, ServiceIdentifier serviceId, String operation, MessageContainer messageContainer) {
            this.meId = meId;
            this.serviceId = serviceId;
            this.operation = operation;
            this.messageContainer = messageContainer;
        }
    }
    
}
