package hr.fer.zemris.ppj.lab01;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FiniteStateAutomaton<S, I> {

    private Set<S> finalStates;
    private Map<S, Function<I, Set<S>>> stateTransitions;
    private S startingState;
    private Set<S> currentStates;

    public FiniteStateAutomaton(Set<S> finalStates, Map<S, Function<I, Set<S>>> stateTransitions, S startingState) {
        super();
        this.finalStates = finalStates;
        this.stateTransitions = stateTransitions;
        this.startingState = startingState;
        currentStates = new HashSet<>();
        currentStates.add(startingState);
    }

    public boolean step ( I input ){
        Set<S> newStates = new HashSet<>();
        for ( S state : currentStates ){
            if (stateTransitions.get(state) == null || stateTransitions.get(state).apply(input) == null ){
                continue;
            }
            newStates.addAll(stateTransitions.get(state).apply(input));
        }

        if ( newStates.isEmpty() ){
            return false;
        }

        currentStates = newStates;
        return true;
    }

    public void reset (){
        currentStates = new HashSet<>();
        currentStates.add(startingState);
    }

    public Set<S> getCurrentFinalStates() {
        return currentStates.stream().filter(s -> finalStates.contains(s)).collect(Collectors.toSet());
    }

}
