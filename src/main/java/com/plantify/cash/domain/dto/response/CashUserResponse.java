package com.plantify.cash.domain.dto.response;

import com.plantify.cash.domain.entity.Cash;

import java.time.LocalDateTime;

public record CashUserResponse(
        Long cashBalance,
        Long accumulatedCash,
        Long redeemedCash,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static CashUserResponse from(Cash cash) {
        return new CashUserResponse(
                cash.getCashBalance(),
                cash.getAccumulatedCash(),
                cash.getRedeemedCash(),
                cash.getCreatedAt(),
                cash.getUpdatedAt()
        );
    }

    public static CashUserResponse empty() {
        return new CashUserResponse(
                0L,
                0L,
                0L,
                null,
                null
        );
    }
}