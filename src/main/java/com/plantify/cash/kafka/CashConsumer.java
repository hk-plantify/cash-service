package com.plantify.cash.kafka;

import com.plantify.cash.domain.dto.TransactionStatusMessage;
import com.plantify.cash.service.CashTransactionStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CashConsumer {

    private final CashTransactionStatusService cashTransactionStatusService;

    @KafkaListener(
            topics = "${spring.kafka.topic.transaction-status}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"

    )
    public void handleTransactionStatus(TransactionStatusMessage message) {
        log.info("Received TransactionStatusMessage: {}", message);
        try {
            switch (message.status()) {
                case PAYMENT -> cashTransactionStatusService.processSuccessfulTransaction(message);
                case FAILED -> cashTransactionStatusService.processFailedTransaction(message);
                default -> log.warn("Unknown status: {}", message.status());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

