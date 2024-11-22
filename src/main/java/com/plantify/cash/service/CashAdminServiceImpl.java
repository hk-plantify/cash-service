package com.plantify.cash.service;

import com.plantify.cash.domain.dto.response.CashAdminResponse;
import com.plantify.cash.domain.dto.resquest.CashAdminRequest;
import com.plantify.cash.domain.dto.resquest.CashGrantRequest;
import com.plantify.cash.domain.entity.Cash;
import com.plantify.cash.domain.entity.Type;
import com.plantify.cash.global.exception.ApplicationException;
import com.plantify.cash.global.exception.errorCode.CashErrorCode;
import com.plantify.cash.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CashAdminServiceImpl implements CashAdminService {

    private final CashRepository cashRepository;

    @Override
    public CashAdminResponse createCashByUserId(Long userId, CashAdminRequest request) {
        Cash cash = cashRepository.findByUserId(userId).orElse(
                Cash.builder()
                        .userId(userId)
                        .cashBalance(0L)
                        .accumulatedCash(0L)
                        .redeemedCash(0L)
                        .type(Type.valueOf(request.type()))
                        .build()
        );

        Cash updatedCash = cash.toBuilder()
                .cashBalance(cash.getCashBalance() + request.amount())
                .accumulatedCash(cash.getAccumulatedCash() + request.amount())
                .build();

        cashRepository.save(updatedCash);

        return CashAdminResponse.from(updatedCash);
    }

    @Override
    public CashAdminResponse getCashByUserId(Long userId) {
        Cash cash = cashRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(CashErrorCode.USER_NOT_FOUND));

        return CashAdminResponse.from(cash);
    }

    @Override
    public void deleteAllCashByUserId(Long userId) {
        Cash cash = cashRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(CashErrorCode.USER_NOT_FOUND));

        cashRepository.delete(cash);
    }

    @Override
    public void deductCashByUserId(Long userId, Long amountToDeduct) {
        Cash cash = cashRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(CashErrorCode.USER_NOT_FOUND));

        if (cash.getCashBalance() < amountToDeduct) {
            throw new ApplicationException(CashErrorCode.INSUFFICIENT_BALANCE);
        }

        Cash updatedCash = cash.toBuilder()
                .cashBalance(cash.getCashBalance() - amountToDeduct)
                .redeemedCash(cash.getRedeemedCash() + amountToDeduct)
                .build();

        cashRepository.save(updatedCash);
    }

    @Override
    public void rewardCash(CashGrantRequest request) {
        List<Cash> cashEntities = request.toEntities();

        cashRepository.saveAll(cashEntities);
    }
}
