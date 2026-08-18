package ru.shulmin.pavel.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProcessor {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessageProcessor.class);

    private static final String SUFFIX = "123";
    private static final String OUTPUT_TOPIC = "output";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Counter messagesProcessedCounter;

    public KafkaMessageProcessor(KafkaTemplate<String, String> kafkaTemplate, MeterRegistry meterRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagesProcessedCounter = meterRegistry.counter("kafka.messages.processed", "topic", "input");
    }

    @KafkaListener(topics = "input", groupId = "stub-consumer-group")
    public void consume(String message) {
        log.debug("Получено сообщение из input: {}", message);

        try {
            String processedMessage = processMessage(message);
            kafkaTemplate.send(OUTPUT_TOPIC, message, processedMessage);
            log.debug("Отправлено обработанное сообщение в output: {}", processedMessage);
            messagesProcessedCounter.increment();
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения: {}", message, e);
        }
    }

    private String processMessage(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }
        return message + SUFFIX;
    }
}
