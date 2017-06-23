package ekart.com.hackapp.fsm;

import lombok.Data;

/**
 * Created by subrat.panda on 23/06/17.
 */

@Data
public class StateEntity {
    private DataType dataType;
    private Object data;
    private String nextQuestion;
}
