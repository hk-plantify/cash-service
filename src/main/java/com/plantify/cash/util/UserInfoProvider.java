package com.plantify.cash.util;

import com.plantify.cash.client.AuthServiceClient;
import com.plantify.cash.domain.dto.response.AuthUserResponse;
import com.plantify.cash.global.exception.ApplicationException;
import com.plantify.cash.global.exception.errorCode.AuthErrorCode;
import com.plantify.cash.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoProvider {

    private final AuthServiceClient authServiceClient;

    public AuthUserResponse getUserInfo(String authorizationHeader) {
        ApiResponse<AuthUserResponse> response = authServiceClient.getUserInfo(authorizationHeader);
        if (response.getStatus() == HttpStatus.OK) {
            return response.getData();
        } else {
            throw switch (response.getStatus()) {
                case UNAUTHORIZED -> new ApplicationException(AuthErrorCode.INVALID_TOKEN);
                case FORBIDDEN -> new ApplicationException(AuthErrorCode.ACCESS_TOKEN_NULL);
                default -> new ApplicationException(AuthErrorCode.UNSUPPORTED_TOKEN);
            };
        }
    }
}
