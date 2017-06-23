package ekart.com.hackapp.fsm.actions;

import java.util.List;

import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.fsm.events.StateEvent;
import ekart.com.hackapp.models.ItemDetail;
import ekart.com.hackapp.networkHandler.NetworkHandler;
import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class ShowProductsAction implements Action {
    @Override
    public ActionResponse execute(StateEvent event, State currentState, List<State> stateList) {
        List<ItemDetail> response = NetworkHandler.getInstance().getProductsForCategory(event.getEventData().toString(), event.getInputType());
        ActionResponse response1 = new ActionResponse();
        response1.actionResponse = response;
        response1.type = ActionResponseType.PRODUCT_LIST;
        return response1;
    }
}
