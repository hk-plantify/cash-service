package com.plantify.cash.domain.entity;

import java.io.Serializable;

public enum Status implements Serializable {

    CHARGE,
    PENDING,
    PAYMENT,
    REFUND,
    CANCELLATION,
    SUCCESS,
    FAILED
}