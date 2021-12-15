package com.doghandeveloper.doggu.account.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthenticationResponse {
    private String key;

    @Builder
    public EmailAuthenticationResponse(String key) {
        this.key = key;
    }
}