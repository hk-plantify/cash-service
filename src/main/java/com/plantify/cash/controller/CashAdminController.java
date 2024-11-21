package com.plantify.cash.controller;

import com.plantify.cash.domain.dto.response.CashAdminResponse;
import com.plantify.cash.domain.dto.resquest.CashAdminRequest;
import com.plantify.cash.global.response.ApiResponse;
import com.plantify.cash.service.CashAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/cash")
public class CashAdminController {

    private final CashAdminService cashAdminService;

    // 캐시 단체 지급
    @PostMapping
    public ResponseEntity<ApiResponse<CashAdminResponse>> createCash(@RequestBody CashAdminRequest request) {
        CashAdminResponse response = cashAdminService.createCash(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 사용자 캐시 추간
    @PostMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<CashAdminResponse>> createCashByUser(
            @PathVariable Long userId, @RequestBody CashAdminRequest request) {
        CashAdminResponse response = cashAdminService.createCashByUser(userId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 사용자 현재 캐시 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<CashAdminResponse>> updateCashByUserId(@PathVariable Long userId) {
        CashAdminResponse response = cashAdminService.updateCashByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 사용자 캐시 차감
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteCashByUserId(@PathVariable Long userId) {
        cashAdminService.deleteCashByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
