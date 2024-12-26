package com.plantify.cash.service;

import com.plantify.cash.domain.dto.TransactionStatusMessage;
import com.plantify.cash.domain.entity.Cash;
import com.plantify.cash.domain.entity.Type;
import com.plantify.cash.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CashTransactionStatusServiceImpl implements CashTransactionStatusService {

    private final CashRepository cashRepository;
    private static final double CASH_REWARD_RATE = 0.2;

    // 성공
    @Override
    public void processSuccessfulTransaction(TransactionStatusMessage message) {
        try {
            Cash cash = cashRepository.findByUserId(message.userId())
                    .orElseGet(() -> cashRepository.save(
                            new Cash().init(message.userId(), Type.GRANT)
                    ));
            cash.increase((long) (message.amount() * CASH_REWARD_RATE));
            cashRepository.save(cash);
        } catch (Exception e) {
            log.error("Error handling transaction status message: {}", e.getMessage());
        }
    }

    // 실패
    @Override
    public void processFailedTransaction(TransactionStatusMessage message) {

    }
}
