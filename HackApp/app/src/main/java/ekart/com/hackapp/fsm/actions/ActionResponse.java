package ekart.com.hackapp.fsm.actions;

import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class ActionResponse {
    public ActionResponseType type;
    public Object actionResponse;
}
