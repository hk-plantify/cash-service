package com.plantify.cash.domain.dto;

import com.plantify.cash.domain.entity.Status;

public record TransactionStatusMessage(
        Long transactionId,
        Long userId,
        String orderId,
        Long amount,
        Status status
){}