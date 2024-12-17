package com.plantify.cash.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long cashId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long cashBalance;

    @Column(nullable = false)
    private Long accumulatedCash;

    @Column(nullable = false)
    private Long redeemedCash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Cash init(Long userId, Type type) {
        this.userId = userId;
        this.cashBalance = 0L;
        this.accumulatedCash = 0L;
        this.redeemedCash = 0L;
        this.type = type;
        return this;
    }

    public Cash increase(long amount) {
        this.cashBalance += amount;
        this.accumulatedCash += amount;
        return this;
    }

    public Cash decrease(long amount) {
        this.cashBalance -= amount;
        this.accumulatedCash += amount;
        this.redeemedCash += amount;
        return this;
    }

    public Cash updateType(Type type) {
        this.type = type;
        return this;
    }
}
