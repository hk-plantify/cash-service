package com.plantify.cash;

import com.plantify.cash.domain.dto.response.AuthUserResponse;
import com.plantify.cash.domain.dto.resquest.CashUserRequest;
import com.plantify.cash.domain.entity.Cash;
import com.plantify.cash.domain.entity.Type;
import com.plantify.cash.global.exception.ApplicationException;
import com.plantify.cash.global.util.UserInfoProvider;
import com.plantify.cash.repository.CashRepository;
import com.plantify.cash.service.CashUserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
public class CashServiceConcurrencyTest {

    @Autowired
    CashUserService cashUserService;

    @Autowired
    CashRepository cashRepository;

    @MockBean
    UserInfoProvider userInfoProvider;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        cashRepository.deleteAll();
        given(userInfoProvider.getUserInfo())
                .willReturn(new AuthUserResponse(1L, "USER"));
    }

    @Test
    @Transactional
    void 트랜잭션_경계_테스트() {
        // given
        cashRepository.save(
                new Cash().init(1L, Type.GRANT).increase(100)
        );
        em.flush();
        em.clear();

        // when
        try {
            cashUserService.buyByCash(new CashUserRequest(200L));
            throw new AssertionError("예외가 발생해야 함");
        } catch (ApplicationException e) {
            // expected
        }

        em.clear();

        // then
        Cash cash = cashRepository.findByUserId(1L).orElseThrow();
        assertThat(cash.getCashBalance()).isEqualTo(100);
    }

    @Test
    @Transactional
    void 최초_캐시_생성_동시성_테스트() throws InterruptedException {
        // given
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    cashUserService.buyByCash(new CashUserRequest(0L));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        em.clear();

        // then
        List<Cash> all = cashRepository.findAll();
        assertThat(all).hasSize(1);
    }

    @Test
    void 캐시_차감_동시성_테스트() throws InterruptedException {
        // given
        cashRepository.save(
                new Cash().init(1L, Type.GRANT).increase(1000)
        );

        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    cashUserService.buyByCash(new CashUserRequest(100L));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        Cash cash = cashRepository.findByUserId(1L).orElseThrow();
        assertThat(cash.getCashBalance()).isEqualTo(0);
    }
}
