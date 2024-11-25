package com.plantify.cash.controller;

import com.plantify.cash.domain.dto.response.CashUserResponse;
import com.plantify.cash.domain.dto.resquest.CashUserRequest;
import com.plantify.cash.global.response.ApiResponse;
import com.plantify.cash.service.CashUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/cash")
public class CashUserController {
    
    private final CashUserService cashUserService;

    // 캐시로 결제
    @PostMapping("/use")
    public ApiResponse<CashUserResponse> buyByCash(@RequestBody CashUserRequest request) {
        CashUserResponse response = cashUserService.buyByCash(request);
        return ApiResponse.ok(response);
    }

    // 현재 보유 캐시 조회
    @GetMapping
    public ApiResponse<CashUserResponse> getCurrentCash() {
        CashUserResponse response = cashUserService.getCurrentCash();
        return ApiResponse.ok(response);
    }
}
