package com.doghandeveloper.doggu.account.dto.response;

import com.doghandeveloper.doggu.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResponse {
    private String email;
    private String userName;

    @Builder
    public AccountResponse(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }


    public static AccountResponse of(Account account) {
        return AccountResponse.builder()
                .email(account.getEmail())
                .userName(account.getUserName())
                .build();
    }
}
