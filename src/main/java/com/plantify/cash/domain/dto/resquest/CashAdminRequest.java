package com.plantify.cash.domain.dto.resquest;

import com.plantify.cash.domain.entity.Cash;
import com.plantify.cash.domain.entity.Type;

import java.util.List;

public record CashAdminRequest(
        Long amount,
        String type
) {

    public Cash toEntity(Long userId) {
        return Cash.builder()
                .userId(userId)
                .cashBalance(amount)
                .type(Type.valueOf(type))
                .build();
    }
}