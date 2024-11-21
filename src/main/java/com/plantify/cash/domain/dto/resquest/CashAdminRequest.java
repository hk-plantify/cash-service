package com.plantify.cash.domain.dto.resquest;

import com.plantify.cash.domain.entity.Cash;
import com.plantify.cash.domain.entity.Type;

import java.util.List;

public record CashAdminRequest(
        Long userId,
        List<Long> userIds,
        Long amount,
        String type
) {

    public Cash toEntity() {
        return Cash.builder()
                .userId(userId)
                .cashBalance(amount)
                .type(Type.valueOf(type))
                .build();
    }
}