package com.doghandeveloper.doggu.account.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthenticationCodeResponse {
    private Boolean isValidEmail;

    @Builder
    public EmailAuthenticationCodeResponse(Boolean isValidEmail)
    {
        this.isValidEmail = isValidEmail;
    }
}