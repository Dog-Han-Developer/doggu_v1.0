package com.doghandeveloper.doggu.account.service;

import com.doghandeveloper.doggu.account.domain.Account;
import com.doghandeveloper.doggu.account.domain.Dog;
import com.doghandeveloper.doggu.account.dto.request.DogInfoRequest;
import com.doghandeveloper.doggu.account.dto.request.RefreshRequest;
import com.doghandeveloper.doggu.account.dto.request.SignupRequest;
import com.doghandeveloper.doggu.account.dto.response.RefreshResponse;
import com.doghandeveloper.doggu.account.repository.AccountRepository;
import com.doghandeveloper.doggu.account.repository.DogRepository;
import com.doghandeveloper.doggu.common.exception.AuthException;
import com.doghandeveloper.doggu.common.exception.EntityException;
import com.doghandeveloper.doggu.common.exception.ErrorCode;
import com.doghandeveloper.doggu.common.security.CustomUserDetailsService;
import com.doghandeveloper.doggu.common.security.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AccountService {

    @Value("${app.token.authorizationHeader}")
    private String authorizationHeader;

    @Value("${app.token.bearerPrefix}")
    private String bearerPrefix;

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;
    private final DogRepository dogRepository;

    public void createAcount(SignupRequest signupRequest) {
        if (accountRepository.existsByEmail(signupRequest.getEmail())) {
            throw new AuthException(ErrorCode.DUPLICATED_USER_ID);
        }
        if (accountRepository.existsByUserName(signupRequest.getUserName())) {
            throw new AuthException(ErrorCode.DUPLICATED_NICKNAME);
        }
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        Account account = signupRequest.toAccount();
        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public void checkDuplicateUserName(String userName) {
        if(accountRepository.existsByUserName(userName)){
            throw new AuthException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }

    public void saveDogInfo(DogInfoRequest dogInfoRequest, Account account) {
        Dog dog = dogInfoRequest.toDog(account);
        dogRepository.save(dog);
    }

    public void updateDogModel(String dogModel, Account account){
        // ????????? ????????? ?????? ??????
        account = accountRepository.findById(account.getId()).orElseThrow(()->{
            return new EntityException(ErrorCode.ENTITY_NOT_FOUND);
        });
        account.updateDogModel(dogModel);
    }

    public RefreshResponse refreshAccessToken(RefreshRequest refreshRequest, HttpServletRequest request) {
        String refreshToken = refreshRequest.getRefreshToken();
        // refresh token??? ????????? ????????? accessToken?????? userId??? ??????
        if (refreshToken != null) {
            String accessToken = getAccessTokenFromRequest(request);
            String userId;
            try {
                userId = tokenProvider.getUserIdFromToken(accessToken);
            } catch (ExpiredJwtException e) {
                userId = e.getClaims().getSubject();
                log.debug("Access Token is expired");
            }
            // refreshToken??? ???????????? ??????(???????????? ?????? ??? ?????? ??????)
            if (tokenProvider.validateRefreshToken(refreshToken, userId)) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                return new RefreshResponse(tokenProvider.createAccessToken(usernamePasswordAuthenticationToken));
            }
        }
        // refresh token??? ????????? exception
        throw new AuthException(ErrorCode.BAD_REFRESH);
    }

    // Request Header?????? Access Token ?????? ????????????
    private String getAccessTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(authorizationHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(bearerPrefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
