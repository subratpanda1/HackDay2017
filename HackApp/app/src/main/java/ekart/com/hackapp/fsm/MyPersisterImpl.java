package ekart.com.hackapp.fsm;

/**
 * Created by subrat.panda on 23/06/17.
 */

import org.statefulj.fsm.Persister;
import org.statefulj.fsm.StaleStateException;
import org.statefulj.fsm.model.State;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Thread safe, in memory Persister.
 *
 * @author Andrew Hall
 *
 */
public class MyPersisterImpl<T> implements Persister<T> {

    private final Map<String, State<T>> states = new HashMap<String, State<T>>();
    private State<T> startState;
    private State<T> currentState;
    private T currentStateFul;
    private String stateFieldName;

    public MyPersisterImpl() {}

    public MyPersisterImpl(final Collection<State<T>> states, final State<T> startState) {
        setStartState(startState);
        setStates(states);
    }

    public MyPersisterImpl(List<State<T>> states, State<T> startState, String stateFieldName) {
        this(states, startState);
        this.stateFieldName = stateFieldName;
    }

    public MyPersisterImpl(T stateful, List<State<T>> states, State<T> startState) {
        this(states, startState);
        this.setCurrent(stateful, startState);
    }

    public MyPersisterImpl(T stateful, List<State<T>> states, State<T> startState, String stateFieldName) {
        this(states, startState, stateFieldName);
        this.setCurrent(stateful, startState);
    }

    public synchronized Collection<State<T>> getStates() {
        return states.values();
    }

    public synchronized State<T> addState(final State<T> state) {
        return states.put(state.getName(), state);
    }

    public synchronized State<T> removeState(final State<T> state) {
        return removeState(state.getName());
    }

    public synchronized State<T> removeState(final String name) {
        return states.remove(name);
    }

    @Override
    public synchronized void setStates(final Collection<State<T>> states) {
        //Clear the map
        //
        this.states.clear();

        //Add new states
        //
        for(State state : states) {
            this.states.put(state.getName(), state);
        }
    }

    public State<T> getStartState() {
        return startState;
    }

    @Override
    public void setStartState(final State<T> startState) {
        this.startState = startState;
    }

    public String getStateFieldName() {
        return stateFieldName;
    }

    @Override
    public State<T> getCurrent(T stateful) {
        try {
            return currentState != null ? currentState : startState;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setCurrent(T stateful, State<T> current) {
        synchronized(stateful) {
            try {
                this.currentStateFul = stateful;
                this.currentState = current;
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * Serialize all update of state.  Ensure that the current state is the same State that
     * was evaluated. If not, throw an exception
     *
     * (non-Javadoc)
     * @see org.fsm.Persister#setCurrent(org.fsm.model.State, org.fsm.model.State)
     */
    @Override
    public void setCurrent(T stateful, State<T> current, State<T> next) throws StaleStateException {
        synchronized(stateful) {
            if (this.getCurrent(stateful).equals(current)) {
                this.setCurrent(stateful, next);
            } else {
                throw new StaleStateException();
            }
        }
    }
}

