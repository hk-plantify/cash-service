package com.plantify.cash.domain.dto;

public record TransactionStatusMessage(
        Long transactionId,
        Long userId,
        String orderId,
        Long amount,
        String status
){}