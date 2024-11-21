package com.plantify.cash.service;

import com.plantify.cash.domain.dto.response.CashAdminResponse;
import com.plantify.cash.domain.dto.resquest.CashAdminRequest;

public interface CashAdminService {

    CashAdminResponse createCash(CashAdminRequest request);
    CashAdminResponse createCashByUserId(Long userId, CashAdminRequest request);
    CashAdminResponse updateCashByUserId(Long userId);
    void deleteCashByUserId(Long userId);
}
