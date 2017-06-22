package ekart.com.hackapp.fsm;

/**
 * Created by subrat.panda on 22/06/17.
 */

public interface FSMStates {
    int LIST_CATEGORY = 0;
    int CATEGORIES_LISTED = 2;
    int SELECT_CATEGORY = 3;
    int CONFIRM_CATEGORY = 4;
    int PRODUCTS_LISTED = 5;
    int SELECT_PRODUCT = 3;
    int CONFIRM_PRODUCT = 4;
    int FINAL_LOOP = 5;
}
