package ekart.com.hackapp.fsm.actions;

import java.util.List;

import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.fsm.events.StateEvent;

/**
 * Created by subrat.panda on 23/06/17.
 */

public interface Action {
    ActionResponse execute(StateEvent event, State currentState, List<State> stateList);
}
