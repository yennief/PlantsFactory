package org.justynafraczek.plantsshop.warehouse;

import java.util.Timer;
import java.util.TimerTask;

import org.justynafraczek.plantsshop.NewPlantsOrderResponse;
import org.justynafraczek.plantsshop.types.ProcessingEvent;
import org.justynafraczek.plantsshop.types.ProcessingState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Dispatcher {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    OrdersDatabase ordersDatabase;

    private ObjectMapper mapper = new ObjectMapper();

    Timer timer = new Timer();
    //czekamy 40s, po nich wykonujemy funkcje nizej
    public void dispatch(String orderID) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ProcessingState currentStatus = ordersDatabase.getOrder(orderID).getPlantsOrder().getStatus();
                // Don't try to send order if it was cancelled or is being refunded
                if (currentStatus == ProcessingState.REFUNDING_ORDER
                        || currentStatus == ProcessingState.ORDER_CANCELLED) {
                    return;
                }
                NewPlantsOrderResponse statusUpdate = new NewPlantsOrderResponse();
                statusUpdate.setOrderID(orderID);
                // po 40s order jest zmieniany na complete, po stanie order being prepared
                statusUpdate.setEvent(ProcessingEvent.COMPLETE);
                try {
                    String res = mapper.writeValueAsString(statusUpdate);
                    //wysylanie stanu complete do bramy i payments
                    kafkaProducer.sendMessage(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 40000);
    }

}
