package api.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "monitor-status";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendStatus(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
} 