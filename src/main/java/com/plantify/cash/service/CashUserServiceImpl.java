package com.plantify.cash.service;

import com.plantify.cash.domain.dto.response.CashUserResponse;
import com.plantify.cash.domain.dto.resquest.CashUserRequest;
import com.plantify.cash.domain.entity.Cash;
import com.plantify.cash.global.exception.ApplicationException;
import com.plantify.cash.global.exception.errorCode.CashErrorCode;
import com.plantify.cash.repository.CashRepository;
import com.plantify.cash.global.util.UserInfoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashUserServiceImpl implements CashUserService {

    private final CashRepository cashRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    public CashUserResponse buyByCash(CashUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();
        Cash currentCash = cashRepository.findByUserId(userId)
                .orElse(request.toEntity(userId));

        if (currentCash.getCashBalance() < request.amount()) {
            throw new ApplicationException(CashErrorCode.INSUFFICIENT_BALANCE);
        }

        Cash updatedCash = currentCash.toBuilder()
                .cashBalance(currentCash.getCashBalance() - request.amount())
                .redeemedCash(currentCash.getRedeemedCash() + request.amount())
                .build();
        cashRepository.save(updatedCash);

        return CashUserResponse.from(updatedCash);
    }

    @Override
    public CashUserResponse getCurrentCash() {
        Long userId = userInfoProvider.getUserInfo().userId();
        return cashRepository.findByUserId(userId)
                .map(CashUserResponse::from)
                .orElse(CashUserResponse.empty());
    }
}