package ekart.com.hackapp.fsm.actions;

import android.content.ClipData;

import java.util.List;

import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.fsm.StateName;
import ekart.com.hackapp.fsm.events.StateEvent;
import ekart.com.hackapp.models.ItemDetail;
import ekart.com.hackapp.networkHandler.NetworkHandler;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class AddProductAction implements Action {
    @Override
    public ActionResponse execute(StateEvent event, State currentState, List<State> stateList) {
        if (currentState.getStateName() == StateName.PRODUCT_CONFIRMATION_ASKED) {
            // TODO: Reject current question

            List<ItemDetail> response = NetworkHandler.getInstance().searchProduct(event.getEventData().toString(), event.getInputType());
            ActionResponse response1 = new ActionResponse();
            response1.setType(ActionResponseType.ADD_PRODUCT);
            response1.setActionResponse(response);
            return response1;
        } else {
            List<ItemDetail> response = NetworkHandler.getInstance().searchProduct(event.getEventData().toString(), event.getInputType());
            ActionResponse response1 = new ActionResponse();
            response1.setType(ActionResponseType.ADD_PRODUCT);
            response1.setActionResponse(response);
            return response1;
        }
    }

}
