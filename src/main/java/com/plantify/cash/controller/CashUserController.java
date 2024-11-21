package com.plantify.cash.controller;

import com.plantify.cash.domain.dto.response.CashUserResponse;
import com.plantify.cash.domain.dto.resquest.CashUserRequest;
import com.plantify.cash.global.response.ApiResponse;
import com.plantify.cash.service.CashUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/cash")
public class CashUserController {
    
    private final CashUserService cashUserService;

    @PostMapping("/use")
    public ResponseEntity<ApiResponse<CashUserResponse>> buyByCash(@RequestBody CashUserRequest request) {
        CashUserResponse response = cashUserService.buyByCash();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CashUserResponse>> getCurrentCash() {
        CashUserResponse response = cashUserService.getCurrentCash();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
