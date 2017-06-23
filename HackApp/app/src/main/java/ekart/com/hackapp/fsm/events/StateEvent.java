package ekart.com.hackapp.fsm.events;

import ekart.com.hackapp.fsm.DataType;
import ekart.com.hackapp.fsm.InputType;
import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class StateEvent {
    private EventName eventName;
    private DataType dataType;
    private Object eventData;
    private InputType inputType;

    public StateEvent(EventName eventName) {
        this.eventName = eventName;
    }
}
