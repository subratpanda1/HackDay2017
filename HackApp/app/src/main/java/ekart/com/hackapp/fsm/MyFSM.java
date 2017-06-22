package ekart.com.hackapp.fsm;

import android.util.Log;

import org.statefulj.fsm.FSM;
import org.statefulj.fsm.model.Action;
import org.statefulj.fsm.model.State;
import org.statefulj.fsm.model.impl.StateImpl;
import org.statefulj.persistence.memory.MemoryPersisterImpl;

import java.util.LinkedList;
import java.util.List;

import ekart.com.hackapp.fsm.events.EventName;
import ekart.com.hackapp.fsm.actions.HelloAction;
import ekart.com.hackapp.fsm.events.StateEvent;

/**
 * Created by subrat.panda on 23/06/17.
 */

public class MyFSM {

    private static MyFSM instance;
    private FSM<StateEntity> fsm;

    public static MyFSM getInstance() {
        if (instance == null) instance = new MyFSM();
        return instance;
    }

    private MyFSM() {
        State<StateEntity> stateA = new StateImpl<>("State A");
        State<StateEntity> stateB = new StateImpl<>("State B");
        State<StateEntity> stateC = new StateImpl<>("State C", true); // End State

        Action<StateEntity> actionA = new HelloAction("World");
        Action<StateEntity> actionB = new HelloAction("Folks");

        stateA.addTransition(EventName.DUMMY_EVENT.name(), stateB, actionA);

        List<State<StateEntity>> states = new LinkedList<>();
        states.add(stateA);
        states.add(stateB);
        states.add(stateC);

        MyPersisterImpl<StateEntity> persister =
                new MyPersisterImpl<>(
                        states,   // Set of States
                        stateA);  // Start State

        fsm = new FSM<>("Foo FSM", persister);
    }

    public void handleEvent(StateEvent event) {
        StateEntity stateEntity = new StateEntity();
        try {
            fsm.onEvent(stateEntity, event.eventName.name());
        } catch (Exception ex) {
            Log.e("ERROR", "Exception: " + ex);
        }
    }
}
