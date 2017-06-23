package ekart.com.hackapp.fsm.actions;

import java.util.List;
import java.util.Map;

import ekart.com.hackapp.fsm.State;
import ekart.com.hackapp.fsm.events.StateEvent;
import ekart.com.hackapp.models.Category;
import ekart.com.hackapp.networkHandler.NetworkHandler;
import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class ShowCategoriesAction implements Action {
    @Override
    public ActionResponse execute(StateEvent event, State currentState, List<State> stateList) {
        List<Category> response = NetworkHandler.getInstance().getCategories();
        ActionResponse response1 = new ActionResponse();
        response1.actionResponse = response;
        response1.type = ActionResponseType.CATEGORY_LIST;
        return response1;
    }
}
