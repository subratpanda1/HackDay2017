package ekart.com.hackapp.fsm.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class ActionMap {
    private static ActionMap instance = null;

    public static ActionMap getInstance() {
        if (instance == null) instance = new ActionMap();
        return instance;
    }

    public Map<ActionType, Action> actionMap;

    ActionMap() {
        actionMap = new HashMap<>();
        actionMap.put(ActionType.DUMMY_ACTION, new DummyAction());
        actionMap.put(ActionType.SHOW_CATEGORIES, new ShowCategoriesAction());
        // actionMap.put(ActionType.SHOW_ITEMS, new ShowItemsAction());
    }
}
