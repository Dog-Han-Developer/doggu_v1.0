package com.doghandeveloper.doggu.account.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.mallang.healingmate.account.dto.response
 * RefreshResponse.java
 * @date    2021-05-03 오전 2:58
 * @author  이아영
 *
 * @변경이력
 **/

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshResponse {
    private String accessToken;

    @Builder
    public RefreshResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
