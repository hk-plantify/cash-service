package com.plantify.cash.domain.dto.resquest;

import com.plantify.cash.domain.entity.Type;

public record CashAdminRequest(
        Long amount,
        Type type
) {
}