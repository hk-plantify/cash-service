package com.plantify.cash.service;

import com.plantify.cash.domain.dto.TransactionStatusMessage;

public interface CashTransactionStatusService {

    void processSuccessfulTransaction(TransactionStatusMessage message);
    void processFailedTransaction(TransactionStatusMessage message);
}
