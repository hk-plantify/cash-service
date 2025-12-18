package com.plantify.cash.service;

import com.plantify.cash.domain.dto.response.CashUserResponse;
import com.plantify.cash.domain.dto.resquest.CashUserRequest;
import com.plantify.cash.domain.entity.Cash;
import com.plantify.cash.domain.entity.Type;
import com.plantify.cash.global.util.UserInfoProvider;
import com.plantify.cash.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashUserServiceImpl implements CashUserService {

    private final CashRepository cashRepository;
    private final UserInfoProvider userInfoProvider;

    @Override
    @Transactional
    public CashUserResponse buyByCash(CashUserRequest request) {
        Long userId = userInfoProvider.getUserInfo().userId();

        Cash cash = cashRepository.findByUserIdForUpdate(userId)
                .orElseGet(() -> cashRepository.save(
                        new Cash().init(userId, Type.USE)
                ));

        cash.decrease(request.amount());
        return CashUserResponse.from(cash);
    }

    @Override
    public CashUserResponse getCurrentCash() {
        Long userId = userInfoProvider.getUserInfo().userId();
        return cashRepository.findByUserId(userId)
                .map(CashUserResponse::from)
                .orElse(CashUserResponse.empty());
    }
}