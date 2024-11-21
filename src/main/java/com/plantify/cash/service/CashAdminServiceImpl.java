package com.plantify.cash.service;

import com.plantify.cash.domain.dto.response.CashAdminResponse;
import com.plantify.cash.domain.dto.resquest.CashAdminRequest;
import com.plantify.cash.domain.entity.Cash;
import com.plantify.cash.global.exception.ApplicationException;
import com.plantify.cash.global.exception.errorCode.CashErrorCode;
import com.plantify.cash.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashAdminServiceImpl implements CashAdminService {

    private final CashRepository cashRepository;

    @Override
    public CashAdminResponse createCash(CashAdminRequest request) {
        Cash cash = cashRepository.save(request.toEntity());
        return CashAdminResponse.from(cash);
    }

    @Override
    public CashAdminResponse createCashByUserId(Long userId, CashAdminRequest request) {
        Cash existingCash = cashRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(CashErrorCode.USER_NOT_FOUND));

        Cash updatedCash = existingCash.toBuilder()
                .cashBalance(existingCash.getCashBalance() + request.amount())
                .accumulatedCash(existingCash.getAccumulatedCash() + request.amount())
                .build();
        cashRepository.save(updatedCash);

        return CashAdminResponse.from(updatedCash);
    }

    @Override
    public CashAdminResponse updateCashByUserId(Long userId) {
        Cash cash = cashRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(CashErrorCode.USER_NOT_FOUND));

        return CashAdminResponse.from(cash);
    }

    @Override
    public void deleteCashByUserId(Long userId) {
        Cash cash = cashRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(CashErrorCode.USER_NOT_FOUND));

        cashRepository.delete(cash);
    }
}
