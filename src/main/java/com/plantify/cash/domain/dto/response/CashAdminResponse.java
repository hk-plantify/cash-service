package com.plantify.cash.domain.dto.response;

import com.plantify.cash.domain.entity.Cash;

import java.time.LocalDateTime;

public record CashAdminResponse(
        Long userId,
        Long cashBalance,
        Long accumulatedCash,
        Long redeemedCash,
        String type,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static CashAdminResponse from(Cash cash) {
        return new CashAdminResponse(
                cash.getUserId(),
                cash.getCashBalance(),
                cash.getAccumulatedCash(),
                cash.getRedeemedCash(),
                cash.getType().name(),
                cash.getCreatedAt(),
                cash.getUpdatedAt()
        );
    }
}