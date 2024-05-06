package org.justynafraczek.plantsshop.warehouse;

import org.justynafraczek.plantsshop.types.ProcessingEvent;
import org.justynafraczek.plantsshop.types.ProcessingState;
import org.justynafraczek.plantsshop.warehouse.models.PaymentStatusUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumer {

    @Autowired
    Dispatcher dispatcher;

    @Autowired
    OrdersDatabase ordersDatabase;

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    // consumer nasluchuje tylko na paymentstatusupdate
    @KafkaListener(topics = "paymentStatusUpdate", groupId = "warehouse")
    public void consume(String message) {
        LOGGER.info(String.format("Message received on topic paymentStatusUpdate -> %s", message));
        try {
            PaymentStatusUpdate psu = mapper.readValue(message, PaymentStatusUpdate.class);
            if (psu.getEvent() == ProcessingEvent.PAYMENT_SUCCESSFUL) {
                ordersDatabase.setOrderStatus(psu.getOrderId(), ProcessingState.ORDER_BEING_PREPARED);
                // wyslanie do dispachera. dispacher po 40s zmienia stan na complete
                dispatcher.dispatch(psu.getOrderId());
            } else {
                LOGGER.info("Payment was not successful. Removing.");
                // TODO: Implement removing order from database
            }
        } catch (Exception e) {
            LOGGER.error("There were an error when processing paymentStatusUpdate message");
            e.printStackTrace();
        }
    }
}