package com.doghandeveloper.doggu.account.controller;

import com.doghandeveloper.doggu.account.domain.Dog;
import com.doghandeveloper.doggu.account.dto.request.EmailAuthenticationRequest;
import com.doghandeveloper.doggu.account.dto.request.LoginRequest;
import com.doghandeveloper.doggu.account.dto.request.RefreshRequest;
import com.doghandeveloper.doggu.account.dto.request.SignupRequest;
import com.doghandeveloper.doggu.account.dto.response.AuthResponse;
import com.doghandeveloper.doggu.account.dto.response.EmailAuthenticationCodeResponse;
import com.doghandeveloper.doggu.account.dto.response.EmailAuthenticationResponse;
import com.doghandeveloper.doggu.account.dto.response.RefreshResponse;
import com.doghandeveloper.doggu.account.service.AccountService;
import com.doghandeveloper.doggu.common.util.EmailSendUtil;
import com.doghandeveloper.doggu.common.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;

@Tag(name = "Account", description = "사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final EmailSendUtil emailSendUtil;

    @PostMapping
    @Operation(summary = "회원가입", description = "회원 정보를 전달 받아 저장합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "회원 가입 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "아이디또는 닉네임 중복", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        accountService.createAcount(signupRequest);
        return new ResponseEntity<String>("Created", HttpStatus.CREATED);
    }

    @GetMapping("/{userName}")
    @Operation(summary = "닉네임 중복 체크", description = "닉네임을 전달받아 중복된 닉네임이 있는지 확인합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 체크 성공", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Boolean> checkDuplicateNickname(@PathVariable String userName) {
        Boolean result = accountService.checkDuplicateUserName(userName);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "아이디와 패스워드를 입력해 로그인합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "아이디 또는 패스워드 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Access Token 재발급", description = "Access Token과 Refresh Token을 전달받아 Access Token을 재발급합니다.", security = @SecurityRequirement(name = "Authorization"), responses = {
            @ApiResponse(responseCode = "200", description = "Access Token 재발급 성공", content = @Content(schema = @Schema(implementation = RefreshResponse.class))),
            @ApiResponse(responseCode = "400", description = "refresh token 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "사용자 인증 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<RefreshResponse> refreshAccessToken(@Valid @RequestBody RefreshRequest refreshRequest, HttpServletRequest request) {
        RefreshResponse refreshResponse = accountService.refreshAccessToken(refreshRequest, request);
        return ResponseEntity.ok().body(refreshResponse);
    }

    @PostMapping("/email")
    @Operation(summary = "이메일 인증번호 전송", description = "이메일 인증번호를 전송합니다.", security = @SecurityRequirement(name = "Authorization"), responses = {
            @ApiResponse(responseCode = "200", description = "이메일 인증번호 전송 성공", content = @Content(schema = @Schema(implementation = RefreshResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증번호 전송 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmailAuthenticationResponse> sendEmailAuthenticationCode(@Valid @RequestBody EmailAuthenticationRequest emailAuthenticationRequest, HttpServletRequest request) {
        EmailAuthenticationResponse emailAuthenticationResponse = emailSendUtil.sendEmailAuthenticationCode(emailAuthenticationRequest.getEmail());
        return ResponseEntity.ok().body(emailAuthenticationResponse);
    }
    @PostMapping("/email/{verifiedCode}")
    @Operation(summary = "이메일 인증번호 확인", description = "이메일 인증번호가 유효한지 확인합니다.", security = @SecurityRequirement(name = "Authorization"), responses = {
            @ApiResponse(responseCode = "200", description = "이메일 인증번호 확인 성공", content = @Content(schema = @Schema(implementation = RefreshResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증정보 확인 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmailAuthenticationCodeResponse> checkEmailAuthenticationCode(@Valid @PathVariable String verifiedCode, HttpServletRequest request) {
        Boolean isValidEmail = emailSendUtil.getUserEmailByCode(verifiedCode);
        EmailAuthenticationCodeResponse emailAuthenticationCodeResponse = new EmailAuthenticationCodeResponse(isValidEmail);
        return ResponseEntity.ok().body(emailAuthenticationCodeResponse);
    }
}
