... <Engine interface> ... 

/**
 * Put the executor in the waiting queue for the incoming event.
 * 
 * @param executor
 * @param eventKey
 */
public void addFlowWaitingEvent(FlowExecutor executor, InComingEventKey eventKey);