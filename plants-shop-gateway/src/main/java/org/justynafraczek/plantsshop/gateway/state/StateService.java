package org.justynafraczek.plantsshop.gateway.state;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.justynafraczek.plantsshop.types.ProcessingEvent;
import org.justynafraczek.plantsshop.types.ProcessingState;

public class StateService {
    //string jako orderId, value jako maszyna stanow - kazdy order ma swoja
    private HashMap<String, StateMachine> processingStates = new HashMap<>();

    public StateService(StateMachineBuilder stateMachineBuilder) {
        this.stateMachineBuilder = stateMachineBuilder;
    }

    private StateMachineBuilder stateMachineBuilder = null;
//pobiera maszyne stanow dla danego zamowienia
    public ProcessingState sendEvent(String orderId, ProcessingEvent event) {
        StateMachine stateMachine;
        synchronized (this) {
            stateMachine = processingStates.get(orderId);
            if (stateMachine == null) {
                stateMachine = stateMachineBuilder.build();
                processingStates.put(orderId, stateMachine);
            }
        }
        return stateMachine.sendEvent(event);

    }

    public void removeState(String bookingId) {
        processingStates.remove(bookingId);
    }

}
