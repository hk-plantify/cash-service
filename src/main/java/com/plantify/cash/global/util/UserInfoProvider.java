package com.plantify.cash.global.util;

import com.plantify.cash.domain.dto.response.AuthUserResponse;
import com.plantify.cash.global.exception.ApplicationException;
import com.plantify.cash.global.exception.errorCode.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoProvider {

    public AuthUserResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new ApplicationException(AuthErrorCode.INVALID_TOKEN);
        }

        Long userId = (Long) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                .orElse("UNKNOWN");

        return new AuthUserResponse(userId, role);
    }
}
