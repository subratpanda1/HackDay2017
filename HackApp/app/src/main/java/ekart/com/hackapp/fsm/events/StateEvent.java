package ekart.com.hackapp.fsm.events;

import ekart.com.hackapp.fsm.DataType;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class StateEvent {
    public EventName eventName;
    public DataType dataType;
    public Object eventData;

    public StateEvent(EventName eventName) {
        this.eventName = eventName;
    }
}
