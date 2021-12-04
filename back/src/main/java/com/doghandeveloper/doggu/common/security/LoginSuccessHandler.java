package com.doghandeveloper.doggu.common.security;

import com.doghandeveloper.doggu.account.domain.Account;
import com.doghandeveloper.doggu.account.domain.UserAccount;
import com.doghandeveloper.doggu.account.dto.response.AccountResponse;
import com.doghandeveloper.doggu.account.dto.response.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        Account account = userAccount.getAccount();
        AccountResponse accountResponse = AccountResponse.of(account);

        new ObjectMapper().writeValue(response.getWriter(), new AuthResponse(tokenProvider.createAccessToken(authentication), tokenProvider.createRefreshToken(authentication), accountResponse));
    }
}
