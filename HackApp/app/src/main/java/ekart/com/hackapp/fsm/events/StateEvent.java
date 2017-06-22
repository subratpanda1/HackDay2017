package ekart.com.hackapp.fsm.events;

import java.util.Map;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class StateEvent {
    public EventType eventType;
    public EventName eventName;
    public Map<String, Object> eventData;

    public StateEvent(EventType eventType, EventName eventName) {
        this.eventType = eventType;
        this.eventName = eventName;
    }
}
