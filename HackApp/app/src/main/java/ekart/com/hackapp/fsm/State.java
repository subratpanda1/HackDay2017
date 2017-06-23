package ekart.com.hackapp.fsm;

import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class State {
    private StateName stateName;
    private StateEntity stateEntity;
}
