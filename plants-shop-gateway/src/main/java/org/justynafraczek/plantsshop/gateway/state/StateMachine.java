package org.justynafraczek.plantsshop.gateway.state;

import org.justynafraczek.plantsshop.types.ProcessingEvent;
import org.justynafraczek.plantsshop.types.ProcessingState;
public class StateMachine {

	private ProcessingState state;
	private StateMachineBuilder stateMachineBuilder;

	public StateMachine(ProcessingState initialState, StateMachineBuilder stateMachineBuilder) {
		this.state = initialState;
		this.stateMachineBuilder = stateMachineBuilder;
	}

	public synchronized ProcessingState sendEvent(ProcessingEvent processingEvent) {
		// ProcessingState previousState = state;
		state = stateMachineBuilder.sendEvent(state, processingEvent);
		return state;
	}

}
