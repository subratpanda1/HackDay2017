package ekart.com.hackapp.fsm;

import java.util.ArrayList;
import java.util.List;

import ekart.com.hackapp.fsm.actions.ActionMap;
import ekart.com.hackapp.fsm.actions.ActionResponse;
import ekart.com.hackapp.fsm.actions.ActionResponseType;
import ekart.com.hackapp.fsm.actions.ActionType;
import ekart.com.hackapp.fsm.events.EventName;
import ekart.com.hackapp.fsm.events.StateEvent;
import ekart.com.hackapp.models.AddProductResponse;
import ekart.com.hackapp.models.ItemDetail;

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
        currentState.setStateName(StateName.WELCOME);
        stateList.add(currentState);
    }

    public State handleEvent(String text, InputType inputType) throws Exception {
        // Based on the text and current state, the event is formed
        if ("SHOW CATEGORIES".equals(text)) {
            StateEvent event = new StateEvent(EventName.SHOW_CATEGORIES);
            ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.SHOW_CATEGORIES).execute(event, currentState, stateList);

            if (response.getType() == ActionResponseType.CATEGORY_LIST) {
                List<String> responseStrList = (List<String>) (response.actionResponse);

                List<ItemDetail> itemList = new ArrayList<>();
                for (String str : responseStrList) {
                    ItemDetail itemDetail = new ItemDetail();
                    itemDetail.setName(str);
                    itemList.add(itemDetail);
                }

                StateEntity stateEntity = new StateEntity();
                stateEntity.setDataType(DataType.PRODUCT_LIST);
                stateEntity.setData(itemList);

                currentState = new State();
                currentState.setStateName(StateName.CATEGORIES_LISTED);
                currentState.setStateEntity(stateEntity);
                stateList.add(currentState);
                return currentState;
            }
        } else if (text.contains("SELECT ITEM")) {
            // Based on previous state, choose the appropriate item
            StateEvent event = new StateEvent(EventName.SELECT_ITEM);
            event.setDataType(DataType.ITEM_NAME);
            event.setEventData(text.replace("SELECT ITEM", ""));
        } else if (text.contains("ADD ")) {
            StateEvent event = new StateEvent(EventName.ADD_PRODUCT);
            event.setInputType(inputType);
            event.setDataType(DataType.PRODUCT_NAME);
            event.setEventData(text.replace("ADD ", ""));

            ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.ADD_PRODUCT).execute(event, currentState, stateList);

            if (response.getType() == ActionResponseType.ADD_PRODUCT) {

                StateEntity stateEntity = new StateEntity();
                stateEntity.setDataType(DataType.PRODUCT_LIST);
                stateEntity.setData(response.actionResponse);
                stateEntity.setNextQuestion("Are you sure");

                currentState = new State();
                currentState.setStateName(StateName.PRODUCTS_LISTED);
                currentState.setStateEntity(stateEntity);
                stateList.add(currentState);
                return currentState;
            }

        } else if ("YES".equals(text)) {
            StateEvent event = new StateEvent(EventName.CONFIRMATION);
            event.setInputType(inputType);

            ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.CONFIRMATION).execute(event, currentState, stateList);

            if (response.getType() == ActionResponseType.ADD_PRODUCT) {
                AddProductResponse response1 = (AddProductResponse) response.getActionResponse();

                StateEntity stateEntity = new StateEntity();
                stateEntity.setDataType(DataType.ADD_PRODUCT);
                stateEntity.setData(response1);
                stateEntity.setNextQuestion("Successfully added product to order");

                currentState = new State();
                currentState.setStateName(StateName.FURTHER);
                currentState.setStateEntity(stateEntity);
                stateList.add(currentState);
                return currentState;
            }

        } else if ("NO".equals(text)) {

        } else if (text.contains("MORE")) {

        } else {

        }

        return null;
    }
}
