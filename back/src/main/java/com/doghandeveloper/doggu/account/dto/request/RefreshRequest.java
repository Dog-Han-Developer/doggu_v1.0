package com.doghandeveloper.doggu.account.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Schema(description = "Access Token 재발급 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshRequest {
    @Schema(description = "Refresh Token")
    @NotBlank
    private String refreshToken;
}
