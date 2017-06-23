package ekart.com.hackapp.fsm.actions;

import java.util.List;

import ekart.com.hackapp.fsm.DataType;
import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.fsm.StateName;
import ekart.com.hackapp.fsm.events.StateEvent;
import ekart.com.hackapp.models.ItemDetail;
import ekart.com.hackapp.networkHandler.NetworkHandler;
import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class SelectItemAction implements Action {
    @Override
    public ActionResponse execute(StateEvent event, State currentState, List<State> stateList) {
        // Find out the last listing state
        State tmpState = stateList.get(stateList.size() - 1);

        if (tmpState.stateName == StateName.CATEGORIES_LISTED) {
            // TODO: User can select a number. Need to convert number to the category in the list
            List<ItemDetail> itemDetails = NetworkHandler.getInstance().getItems(event.eventData.toString());
            ActionResponse actionResponse = new ActionResponse();
            actionResponse.setActionResponse(itemDetails);
            actionResponse.setType(ActionResponseType.ITEM_LIST);
            return actionResponse;
        } else {
            ActionResponse actionResponse = new ActionResponse();
            actionResponse.setType(ActionResponseType.INVALID_INPUT);
            actionResponse.setActionResponse("This is an invalid input");
            return actionResponse;
        }
    }
}
