package com.plantify.cash.service;

import com.plantify.cash.domain.dto.response.CashAdminResponse;
import com.plantify.cash.domain.dto.resquest.CashAdminRequest;
import com.plantify.cash.domain.dto.resquest.CashGrantRequest;

public interface CashAdminService {

    CashAdminResponse createCashByUserId(Long userId, CashAdminRequest request);
    CashAdminResponse getCashByUserId(Long userId);
    void deleteAllCashByUserId(Long userId);
    void deductCashByUserId(Long userId, Long amountToDeduct);
    void rewardCash(CashGrantRequest request);
}
