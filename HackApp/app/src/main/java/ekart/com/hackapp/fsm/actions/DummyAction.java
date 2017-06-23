package ekart.com.hackapp.fsm.actions;

import java.util.List;
import java.util.Map;

import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.fsm.events.StateEvent;
import ekart.com.hackapp.networkHandler.NetworkHandler;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class DummyAction implements Action {
    @Override
    public ActionResponse execute(StateEvent event, State currentState, List<State> stateList) {
        Map<String, Long> response = NetworkHandler.getInstance().doHealthCheck();
        ActionResponse response1 = new ActionResponse();
        response1.actionResponse = response;
        response1.type = ActionResponseType.DUMMY_RESPONSE;
        return response1;
    }
}
