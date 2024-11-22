package com.plantify.cash.domain.dto.resquest;

import com.plantify.cash.domain.entity.Cash;
import com.plantify.cash.domain.entity.Type;

import java.util.List;

public record CashGrantRequest(
        List<Long> userIds,
        Long amount,
        String type
) {
    public List<Cash> toEntities() {
        return userIds.stream()
                .map(userId -> Cash.builder()
                        .userId(userId)
                        .cashBalance(amount)
                        .type(Type.valueOf(type))
                        .build())
                .toList();
    }
}