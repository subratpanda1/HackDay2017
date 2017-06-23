package ekart.com.hackapp.fsm.actions;

import java.util.List;

import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.fsm.StateName;
import ekart.com.hackapp.fsm.events.StateEvent;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class ConfirmationAction implements Action {
    @Override
    public ActionResponse execute(StateEvent event, State currentState, List<State> stateList) {
        if (currentState.stateName == StateName.PRODUCT_CONFIRMATION_ASKED) {

            // AddProductResponse response = NetworkHandler.getInstance().addProduct(event.eventData);
            ActionResponse response1 = new ActionResponse();
            response1.setType(ActionResponseType.DUMMY_RESPONSE);
            // response1.setActionResponse(response);
            return response1;
        } else {
            ActionResponse actionResponse = new ActionResponse();
            actionResponse.setType(ActionResponseType.INVALID_INPUT);
            actionResponse.setActionResponse("This is an invalid input");
            return actionResponse;
        }
    }

}
