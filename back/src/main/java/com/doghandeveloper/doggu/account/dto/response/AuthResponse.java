package com.doghandeveloper.doggu.account.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.mallang.healingmate.account.dto.response
 * AuthResponse.java
 * @date    2021-05-01 오후 8:33
 * @author  이아영
 *
 * @변경이력
 **/

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    @JsonProperty("user")
    private AccountResponse accountResponse;

    @Builder
    public AuthResponse(String accessToken, String refreshToken, AccountResponse accountResponse) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accountResponse = accountResponse;
    }
}
