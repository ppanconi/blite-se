package it.unifi.dsi.blitese.engine.runtime;

/**
 * This is interface represents the communication channel between 
 * the Engine and the Communication Environment.  
 * 
 * @author panks
 */
public interface EngineChannel {

    /**
     * This method initialize a communication exchange from 
     * the process invoking process the requested endpoint
     * 
     * @param operation
     * @param messageContainer
     * @param instance the Process Instance initiatin the echange.
     * 
     * @return Object messageExchangeId 
     *         the idetificator key for the protocol state communication.
     *         
     */
     public Object createExchange(ServiceIdentifier serviceId, 
                                  String operation,
                                  ProcessInstance instance);
   
    /**
     * Send a message container into the created exchange. This a created post
     * step in the comunication.
     * 
     * @param  inComingEventKey
     */
    public  void sendIntoExchange(Object messageExchangeId,
                                  MessageContainer messageContainer);

    /**
     * Close the exchange.
     * 
     * @param messageExchangeId
     */
    public void closeExchange(Object messageExchangeId);
}
