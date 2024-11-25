package com.plantify.cash.controller;

import com.plantify.cash.domain.dto.response.CashAdminResponse;
import com.plantify.cash.domain.dto.resquest.CashAdminRequest;
import com.plantify.cash.domain.dto.resquest.CashGrantRequest;
import com.plantify.cash.global.response.ApiResponse;
import com.plantify.cash.service.CashAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/cash")
public class CashAdminController {

    private final CashAdminService cashAdminService;

    // 특정 사용자 캐시 지급
    @PostMapping("/users/{userId}")
    public ApiResponse<CashAdminResponse> createCashByUserId(
            @PathVariable Long userId, @RequestBody CashAdminRequest request) {
        CashAdminResponse response = cashAdminService.createCashByUserId(userId, request);
        return ApiResponse.ok(response);
    }

    // 특정 사용자 현재 캐시 조회
    @GetMapping("/users/{userId}")
    public ApiResponse<CashAdminResponse> getCashByUserId(@PathVariable Long userId) {
        CashAdminResponse response = cashAdminService.getCashByUserId(userId);
        return ApiResponse.ok(response);
    }

    // 특정 사용자 캐시 차감
    @DeleteMapping("/users/{userId}")
    public ApiResponse<Void> deleteCashByUserId(
            @PathVariable Long userId, @RequestParam(required = false) Long amount) {
        if (amount == null) {
            cashAdminService.deleteAllCashByUserId(userId);
        } else {
            cashAdminService.deductCashByUserId(userId, amount);
        }
        return ApiResponse.ok();
    }

    // 보상 캐시 지급
    @PostMapping("/events/reward")
    public ApiResponse<Void> rewardCash(@RequestBody CashGrantRequest request) {
        cashAdminService.rewardCash(request);
        return ApiResponse.ok();
    }
}
