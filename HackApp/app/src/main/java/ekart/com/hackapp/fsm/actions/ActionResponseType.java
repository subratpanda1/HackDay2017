package ekart.com.hackapp.fsm.actions;

/**
 * Created by subrat.panda on 23/06/17.
 */

public enum ActionResponseType {
    CATEGORY_LIST, // List<Category>
    PRODUCT_LIST,  // List<ItemDetail>
    ADD_PRODUCT,   // List<ItemDetail>
    INVALID_INPUT,
    COULD_NOT_UNDERSTAND
}
