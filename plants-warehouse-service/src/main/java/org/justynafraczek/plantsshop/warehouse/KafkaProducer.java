package org.justynafraczek.plantsshop.warehouse;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    //kafka producer wysyla na jeden topic: warehouseorderupdate
    String TOPIC_NAME = "warehouseOrderUpdate";
    String group_id = "";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        LOGGER.info(String.format("Message sent -> %s", message));
        kafkaTemplate.send(TOPIC_NAME, message);
    }
}
