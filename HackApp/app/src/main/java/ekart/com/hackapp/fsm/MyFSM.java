package ekart.com.hackapp.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ekart.com.hackapp.fsm.actions.ActionMap;
import ekart.com.hackapp.fsm.actions.ActionResponse;
import ekart.com.hackapp.fsm.actions.ActionType;
import ekart.com.hackapp.fsm.events.EventName;
import ekart.com.hackapp.fsm.events.StateEvent;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class MyFSM {

    private static MyFSM instance;

    public List<State> stateList;
    public State currentState;


    public static MyFSM getInstance() {
        if (instance == null) instance = new MyFSM();
        return instance;
    }

    private MyFSM() {
        stateList = new ArrayList<>();
        currentState = new State();
        currentState.stateName = StateName.WELCOME;
        stateList.add(currentState);
    }

    public State handleEvent(StateEvent event) {
        switch (event.eventName) {
            case DUMMY_NAME: {
                // Validate Current State

                // Transition to new state
                // Trigger the action corresponding to the event
                ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.DUMMY_ACTION).execute(event, currentState, stateList);

                StateEntity stateEntity = new StateEntity();
                stateEntity.dataType = DataType.DUMMY_DATA_TYPE;
                stateEntity.data = response;

                currentState = new State();
                currentState.stateName = StateName.CATEGORIES_LISTED;
                currentState.stateEntity = stateEntity;
                stateList.add(currentState);
                break;
            }
            case SHOW_CATEGORIES: {
                ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.SHOW_CATEGORIES).execute(event, currentState, stateList);

                StateEntity stateEntity = new StateEntity();
                stateEntity.dataType = DataType.CATEGORY_LIST;
                stateEntity.data = response;

                currentState = new State();
                currentState.stateName = StateName.CATEGORIES_LISTED;
                currentState.stateEntity = stateEntity;
                stateList.add(currentState);
                break;
            }
        }

        return currentState;
    }

    public State handleEvent(String text) throws Exception {
        // Based on the text and current state, the event is formed
        if ("SHOW CATEGORIES".equals(text)) {
            return handleEvent(new StateEvent(EventName.SHOW_CATEGORIES));
        } else if (text.contains("SELECT ITEM")) {
            // Based on previous state, choose the appropriate item
            StateEvent event = new StateEvent(EventName.SELECT_ITEM);
            event.dataType = DataType.ITEM_NAME;
            event.eventData = text.replace("SELECT ITEM", "");
        } else if (text.contains("ADD ITEM")) {

        } else if ("YES".equals(text)) {

        } else if ("NO".equals(text)) {

        } else if (text.contains("MORE")) {

        } else {

        }

        return handleEvent(new StateEvent(EventName.DUMMY_NAME));
    }
}
