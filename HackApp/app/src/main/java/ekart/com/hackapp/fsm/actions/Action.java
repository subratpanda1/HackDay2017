package ekart.com.hackapp.fsm.actions;

import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.fsm.events.StateEvent;

/**
 * Created by subrat.panda on 23/06/17.
 */

public interface Action {
    ActionResponse execute(State currentState, StateEvent event);
}
