package org.justynafraczek.plantsshop.gateway.state;

import java.util.EnumMap;
import java.util.HashMap;
import org.justynafraczek.plantsshop.types.ProcessingEvent;
import org.justynafraczek.plantsshop.types.ProcessingState;

public class StateMachineBuilder {

	private HashMap<ProcessingState, HashMap<ProcessingEvent, ProcessingState>> transitions = new HashMap<>();
	private ProcessingState initialState;

	public StateMachineBuilder initialState(ProcessingState initialState) {
		this.initialState = initialState;
		return this;
	}

	public StateMachineBuilder add(ProcessingState source, ProcessingEvent event, ProcessingState target) {
		HashMap<ProcessingEvent, ProcessingState> transitionsForSource = transitions.get(source);
		if (transitionsForSource == null) {
			transitionsForSource = new HashMap<>();
			transitions.put(source, transitionsForSource);
		}
		transitionsForSource.put(event, target);
		return this;
	}

	public StateMachine build() {
		StateMachine stateMachine = new StateMachine(initialState, this);
		return stateMachine;
	}

	public ProcessingState sendEvent(ProcessingState state, ProcessingEvent processingEvent) {
		HashMap<ProcessingEvent, ProcessingState> transitionsForSource = transitions.get(state);
		ProcessingState targetState = transitionsForSource.get(processingEvent);
		return targetState;
	}
}
