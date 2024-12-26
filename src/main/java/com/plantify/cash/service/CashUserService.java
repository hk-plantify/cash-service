package com.plantify.cash.service;

import com.plantify.cash.domain.dto.response.CashUserResponse;
import com.plantify.cash.domain.dto.resquest.CashUserRequest;

public interface CashUserService {

    CashUserResponse buyByCash(CashUserRequest request);
    CashUserResponse getCurrentCash();
}
