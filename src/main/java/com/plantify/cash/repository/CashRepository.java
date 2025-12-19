package com.plantify.cash.repository;

import com.plantify.cash.domain.entity.Cash;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CashRepository extends JpaRepository<Cash, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Cash c where c.userId = :userId")
    Optional<Cash> findByUserIdForUpdate(@Param("userId") Long userId);
    Optional<Cash> findByUserId(Long userId);
}
