package com.plantify.cash.repository;

import com.plantify.cash.domain.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashRepository extends JpaRepository<Cash, Long> {

    Optional<Cash> findByUserId(Long userId);
}
