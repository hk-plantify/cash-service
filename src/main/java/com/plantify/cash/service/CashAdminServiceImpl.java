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
                new Cash().init(userId, request.type())
        );
        cash.increase(request.amount());
        cashRepository.save(cash);

        return CashAdminResponse.from(cash);
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
        Cash decreased = cash.decrease(amountToDeduct);
        cashRepository.save(decreased);
    }

    @Override
    public void rewardCash(CashGrantRequest request) {
        List<Cash> cashEntities = request.toEntities();

        cashRepository.saveAll(cashEntities);
    }
}
