package ekart.com.hackapp.fsm.events;

import java.util.Map;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class StateEvent {
    public EventName eventName;
    public Map<String, Object> eventData;

    public StateEvent(EventName eventName) {
        this.eventName = eventName;
    }
}
