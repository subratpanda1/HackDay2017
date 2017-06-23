package ekart.com.hackapp.fsm;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ekart.com.hackapp.fsm.actions.ActionMap;
import ekart.com.hackapp.fsm.actions.ActionResponse;
import ekart.com.hackapp.fsm.actions.ActionResponseType;
import ekart.com.hackapp.fsm.actions.ActionType;
import ekart.com.hackapp.fsm.events.EventName;
import ekart.com.hackapp.fsm.events.StateEvent;
import ekart.com.hackapp.models.Category;
import ekart.com.hackapp.models.ItemDetail;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class MyFSM {

    private static MyFSM instance;

    public List<State> stateList;
    public State currentState;

    public List<ItemDetail> purchasedItems = new ArrayList<>();

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

    public State handleEvent(String fulfilledText, String resolvedText, InputType inputType) throws Exception {
        // Based on the text and current state, the event is formed
        if ("SHOW CATEGORIES".equals(fulfilledText) || "SHOW CATEGORIES".equals(resolvedText)) {
            StateEvent event = new StateEvent(EventName.SHOW_CATEGORIES);
            ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.SHOW_CATEGORIES).execute(event, currentState, stateList);

            if (response.getType() == ActionResponseType.CATEGORY_LIST) {
                List<Category> responseStrList = (List<Category>) (response.actionResponse);

                List<ItemDetail> itemList = new ArrayList<>();
                for (Category cat : responseStrList) {
                    ItemDetail itemDetail = new ItemDetail();
                    itemDetail.setName(cat.getName());
                    itemDetail.setImageUrl(cat.getImageUrl());
                    itemDetail.setItemType(ItemDetail.ItemType.CATEGORY);
                    itemList.add(itemDetail);
                }

                StateEntity stateEntity = new StateEntity();
                stateEntity.setDataType(DataType.PRODUCT_LIST);
                stateEntity.setData(itemList);
                stateEntity.setNextQuestion("Which category do you want to add ?");

                currentState = new State();
                currentState.setStateName(StateName.CATEGORIES_LISTED);
                currentState.setStateEntity(stateEntity);
                stateList.add(currentState);
                return currentState;
            }
        } else if (fulfilledText.startsWith("SHOW PRODUCTS")) {
            StateEvent event = new StateEvent(EventName.SHOW_PRODUCTS);
            event.setInputType(inputType);
            event.setDataType(DataType.CATEGORY_NAME);
            event.setEventData(fulfilledText.replace("SHOW PRODUCTS ", ""));

            ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.SHOW_PRODUCTS).execute(event, currentState, stateList);

            if (response.getType() == ActionResponseType.PRODUCT_LIST) {
                StateEntity stateEntity = new StateEntity();
                stateEntity.setDataType(DataType.PRODUCT_NAME);
                stateEntity.setData(response.actionResponse);
                stateEntity.setNextQuestion("Which product do you want to add ?");

                currentState = new State();
                currentState.setStateName(StateName.PRODUCTS_LISTED);
                currentState.setStateEntity(stateEntity);
                stateList.add(currentState);
                return currentState;
            }
        } else if (fulfilledText.startsWith("ADD ")) {
            StateEvent event = new StateEvent(EventName.ADD_PRODUCT);
            event.setInputType(inputType);
            event.setDataType(DataType.PRODUCT_NAME);
            event.setEventData(fulfilledText.replace("ADD ", ""));

            ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.ADD_PRODUCT).execute(event, currentState, stateList);

            if (response.getType() == ActionResponseType.ADD_PRODUCT) {
                StateEntity stateEntity = new StateEntity();
                stateEntity.setDataType(DataType.PRODUCT_LIST);
                stateEntity.setData(response.actionResponse);

                List<ItemDetail> items = (List<ItemDetail>)response.actionResponse;

                if (items.size() == 1) {
                    stateEntity.setNextQuestion("Are you sure you want to buy " + items.get(0).getName() + ": (Yes / No) ?");

                    currentState = new State();
                    currentState.setStateName(StateName.PRODUCT_CONFIRMATION_ASKED);
                    currentState.setStateEntity(stateEntity);
                    stateList.add(currentState);
                    return currentState;
                } else if (items.isEmpty()) {
                    stateEntity.setNextQuestion(("No Items Found"));
                } else {
                    stateEntity.setNextQuestion("Please select one");
                }

                currentState = new State();
                currentState.setStateName(StateName.PRODUCTS_LISTED);
                currentState.setStateEntity(stateEntity);
                stateList.add(currentState);
                return currentState;
            }
        } else if (fulfilledText.startsWith("CONFIRM PRODUCT ")) {
            StateEvent event = new StateEvent(EventName.CONFIRM_PRODUCT);
            event.setInputType(inputType);
            event.setDataType(DataType.PRODUCT_NAME);

            // Find the Product from product list
            State state = stateList.get(stateList.size() - 1);
            List<ItemDetail> items = (List<ItemDetail>)state.getStateEntity().getData();

            String productName = fulfilledText.replace("CONFIRM PRODUCT ", "");
            ItemDetail product = null;
            for (ItemDetail itr : items) {
                if (itr.getName().toUpperCase().equals(productName.toUpperCase())) {
                    product = itr;
                    break;
                }
            }

            StateEntity stateEntity = new StateEntity();
            stateEntity.setDataType(DataType.PRODUCT_LIST);
            stateEntity.setData(Collections.singletonList(product));
            stateEntity.setNextQuestion("Are you sure you want to buy " + product.getName() + ": (Yes / No) ?");

            currentState = new State();
            currentState.setStateName(StateName.PRODUCT_CONFIRMATION_ASKED);
            currentState.setStateEntity(stateEntity);
            stateList.add(currentState);
            return currentState;
        } else if ("YES".equals(fulfilledText) ||
                "YES".equals(resolvedText) ||
                resolvedText.contains("YES YES") ||
                "YEP".equals(fulfilledText) ||
                "YEP".equals(resolvedText) ||
                "OK".equals(fulfilledText) ||
                "OK".equals(resolvedText) ||
                "OKAY".equals(fulfilledText) ||
                "OKAY".equals(resolvedText)) {
            StateEvent event = new StateEvent(EventName.CONFIRMATION);
            event.setInputType(inputType);

            ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.CONFIRMATION).execute(event, currentState, stateList);

            if (response.getType() == ActionResponseType.ADD_PRODUCT) {
                List<ItemDetail> response1 = (List<ItemDetail>) response.getActionResponse();

                StateEntity stateEntity = new StateEntity();
                stateEntity.setDataType(DataType.ADD_PRODUCT);
                stateEntity.setData(response1);
                stateEntity.setNextQuestion("Successfully added " + response1.get(0).getName() + " to order");

                purchasedItems.add(response1.get(0));

                currentState = new State();
                currentState.setStateName(StateName.FURTHER);
                currentState.setStateEntity(stateEntity);
                stateList.add(currentState);
                return currentState;
            }
        } else if ("WHAT IS YOUR NAME".equals(resolvedText)) {
            currentState.getStateEntity().setNextQuestion("My name is shop together");
            return currentState;
        } else if (fulfilledText.startsWith("NO") || resolvedText.startsWith("NO") || resolvedText.contains("NO NO")) {
            currentState.getStateEntity().setNextQuestion("Request Cancelled");
            return currentState;
        } else if (fulfilledText.contains("CHECKOUT") || resolvedText.contains("CHECKOUT") || fulfilledText.contains("CHECK OUT") || resolvedText.contains("CHECK OUT")) {
            StateEntity stateEntity = new StateEntity();

            int totalPrice = 0;
            for (ItemDetail itr : purchasedItems) {
                totalPrice += itr.getPrice();
            }

            stateEntity.setDataType(DataType.PRODUCT_LIST);
            stateEntity.setData(purchasedItems);
            stateEntity.setNextQuestion("Your order is complete. Total amount payable is : " + totalPrice);
            currentState = new State();
            currentState.setStateName(StateName.WELCOME);
            currentState.setStateEntity(stateEntity);
            stateList.add(currentState);
            return currentState;
        } else if ("SHOW CART".equals(fulfilledText) || "SHOW CART".equals(resolvedText)) {
            StateEntity stateEntity = new StateEntity();
            stateEntity.setDataType(DataType.PRODUCT_LIST);
            stateEntity.setData(purchasedItems);

            int totalPrice = 0;
            for (ItemDetail itr : purchasedItems) {
                totalPrice += itr.getPrice();
            }
            stateEntity.setNextQuestion("Your order is complete. Total amount payable is : " + totalPrice);

            currentState = new State();
            currentState.setStateName(StateName.WELCOME);
            currentState.setStateEntity(stateEntity);
            stateList.add(currentState);
            return currentState;


        } else {
            StateEvent event = new StateEvent(EventName.ADD_PRODUCT);
            event.setInputType(inputType);
            event.setDataType(DataType.PRODUCT_NAME);
            event.setEventData(resolvedText);

            ActionResponse response = ActionMap.getInstance().actionMap.get(ActionType.ADD_PRODUCT).execute(event, currentState, stateList);

            if (response.getType() == ActionResponseType.ADD_PRODUCT) {
                StateEntity stateEntity = new StateEntity();
                stateEntity.setDataType(DataType.PRODUCT_LIST);
                stateEntity.setData(response.actionResponse);

                List<ItemDetail> items = (List<ItemDetail>)response.actionResponse;

                if (items.size() == 1) {
                    stateEntity.setNextQuestion("Are you sure you want to buy " + items.get(0).getName() + ": (Yes / No) ?");

                    currentState = new State();
                    currentState.setStateName(StateName.PRODUCT_CONFIRMATION_ASKED);
                    currentState.setStateEntity(stateEntity);
                    stateList.add(currentState);
                    return currentState;
                } else if (items.isEmpty()) {
                    stateEntity.setNextQuestion(("No Items Found"));
                } else {
                    stateEntity.setNextQuestion("Please select one");
                }

                currentState = new State();
                currentState.setStateName(StateName.PRODUCTS_LISTED);
                currentState.setStateEntity(stateEntity);
                stateList.add(currentState);
                return currentState;
            }
        }

        return null;
    }
}
