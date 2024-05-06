package org.justynafraczek.plantsshop.gateway;

import org.apache.camel.CamelContext;
// import org.apache.camel.impl.saga.InMemorySagaService;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.justynafraczek.plantsshop.types.ProcessingEvent;
import org.justynafraczek.plantsshop.types.ProcessingState;
import org.justynafraczek.plantsshop.gateway.state.StateMachineBuilder;
import org.justynafraczek.plantsshop.gateway.state.StateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ApplicationConfig {

	@Bean(name = "basicStateMachineBuilder")
	public StateMachineBuilder basicStateMachineBuilder() {
		StateMachineBuilder smb = new StateMachineBuilder();
		smb.initialState(ProcessingState.NONE)
		//zdefiniowane przejscia maszyny stanow
				.add(ProcessingState.NONE, ProcessingEvent.START, ProcessingState.ORDER_PLACED)
				.add(ProcessingState.NONE, ProcessingEvent.CANCEL_ORDER, ProcessingState.ORDER_CANCELLED)
				.add(ProcessingState.ORDER_PLACED, ProcessingEvent.STORE_NOT_ENOUGH_PLANTS, ProcessingState.ORDER_CANCELLED)
				.add(ProcessingState.ORDER_PLACED, ProcessingEvent.STORE_ENOUGH_PLANTS, ProcessingState.AWAITING_PAYMENT)
                // FROM AWAITING_PAYMENT transitions
				.add(ProcessingState.AWAITING_PAYMENT, ProcessingEvent.CANCEL_ORDER, ProcessingState.ORDER_CANCELLED)
				.add(ProcessingState.AWAITING_PAYMENT, ProcessingEvent.PAYMENT_DECLINED, ProcessingState.ORDER_CANCELLED)
				.add(ProcessingState.AWAITING_PAYMENT, ProcessingEvent.PAYMENT_TIMED_OUT, ProcessingState.ORDER_CANCELLED)
                .add(ProcessingState.AWAITING_PAYMENT, ProcessingEvent.PAYMENT_SUCCESSFUL, ProcessingState.ORDER_BEING_PREPARED)
                // FROM ORDER_BEING_PREPARED transitions
                .add(ProcessingState.ORDER_BEING_PREPARED, ProcessingEvent.CANCEL_ORDER, ProcessingState.REFUNDING_ORDER)
                .add(ProcessingState.ORDER_BEING_PREPARED, ProcessingEvent.COMPLETE, ProcessingState.ORDER_FINISHED)
                // FROM REFUNDING_ORDER transitions
                .add(ProcessingState.REFUNDING_ORDER, ProcessingEvent.CANCEL_ORDER, ProcessingState.ORDER_CANCELLED)
				.add(ProcessingState.ORDER_FINISHED, ProcessingEvent.CANCEL_ORDER, ProcessingState.ORDER_FINISHED)
				.add(ProcessingState.ORDER_CANCELLED, ProcessingEvent.START, ProcessingState.ORDER_CANCELLED)
				.add(ProcessingState.ORDER_CANCELLED, ProcessingEvent.COMPLETE, ProcessingState.ORDER_CANCELLED);
		return smb;
	}

	@Bean
	@Scope("prototype")
	public StateService stateService(@Qualifier("basicStateMachineBuilder") StateMachineBuilder stateMachineBuilder) {
		return new StateService(stateMachineBuilder);
	}
}