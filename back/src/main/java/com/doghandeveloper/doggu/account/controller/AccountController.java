package com.doghandeveloper.doggu.account.controller;

import com.doghandeveloper.doggu.account.domain.Account;
import com.doghandeveloper.doggu.account.dto.request.*;
import com.doghandeveloper.doggu.account.dto.response.AuthResponse;
import com.doghandeveloper.doggu.account.dto.response.EmailAuthenticationCodeResponse;
import com.doghandeveloper.doggu.account.dto.response.EmailAuthenticationResponse;
import com.doghandeveloper.doggu.account.dto.response.RefreshResponse;
import com.doghandeveloper.doggu.account.service.AccountService;
import com.doghandeveloper.doggu.common.exception.ErrorResponse;
import com.doghandeveloper.doggu.common.security.CurrentAccount;
import com.doghandeveloper.doggu.common.util.EmailSendUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@Tag(name = "Account", description = "사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final EmailSendUtil emailSendUtil;
    @Value("${spring.profiles.include}")
    private String apiKey;


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
    @Operation(summary = "사용자이름 중복 체크", description = "사용자이름을 전달받아 중복된 사용자이름이 있는지 확인합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "닉네임 중복 체크 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "사용자이름 중복", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> checkDuplicateNickname(@PathVariable String userName) {
        accountService.checkDuplicateUserName(userName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/dog-info")
    @Operation(summary = "강아지 정보 저장 ", description = "강아지 정보를 전달받아 저장합니다.", security = @SecurityRequirement(name = "Authorization"), responses = {
            @ApiResponse(responseCode = "204", description = "강아지 정보 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> saveDogInfo(@Valid @RequestBody DogInfoRequest dogInfoRequest, @Parameter(hidden = true) @CurrentAccount Account account){
        accountService.saveDogInfo(dogInfoRequest, account);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/dog-model")
    @Operation(summary = "강아지 모델 등록(수정)", description = "강아지 모델을 선택합니다.",  security = @SecurityRequirement(name = "Authorization"), responses = {
            @ApiResponse(responseCode = "204", description = "강아지 모델 등록(수정) 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "사용자 인증 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> updateDogModel(@Schema(description = "강아지 모델", example = "보미") @RequestParam String dogModel, @Parameter(hidden = true) @CurrentAccount Account account){
        accountService.updateDogModel(dogModel, account);
        return ResponseEntity.noContent().build();
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

    @PostMapping("/dog-authentication")
    @Operation(summary = "강아지 인증", description = "견주 이름과 동물등록번호를 입력해 인증을 해줍니다. ",security = @SecurityRequirement(name = "Authorization"), responses = {
            @ApiResponse(responseCode = "200", description = "인증 성공", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "견주 이름 또는 동물등록번호 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> verifiedDog(@Valid @RequestBody DogRegistrationRequest dogRegistrationRequest) throws IOException {


        StringBuilder urlBuilder = new StringBuilder("http://openapi.animal.go.kr/openapi/service/rest/animalInfoSrvc/animalInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + apiKey);
        urlBuilder.append("&" + URLEncoder.encode("dog_reg_no","UTF-8") + "=" + URLEncoder.encode(dogRegistrationRequest.getRegisterNumber(), "UTF-8")); /*동물등록번호*/
        urlBuilder.append("&" + URLEncoder.encode("owner_nm","UTF-8") + "=" + URLEncoder.encode(dogRegistrationRequest.getOwnerName(), "UTF-8")); /*소유자 성명*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        return ResponseEntity.ok().body(sb.toString());
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
    @Operation(summary = "이메일 인증번호 전송", description = "이메일 인증번호를 전송합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "이메일 인증번호 전송 성공", content = @Content(schema = @Schema(implementation = RefreshResponse.class))),
            @ApiResponse(responseCode = "400", description = "인증번호 전송 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmailAuthenticationResponse> sendEmailAuthenticationCode(@Valid @RequestBody EmailAuthenticationRequest emailAuthenticationRequest, HttpServletRequest request) {
        EmailAuthenticationResponse emailAuthenticationResponse = emailSendUtil.sendEmailAuthenticationCode(emailAuthenticationRequest.getEmail());
        return ResponseEntity.ok().body(emailAuthenticationResponse);
    }
    @PostMapping("/email/{verifiedCode}")
    @Operation(summary = "이메일 인증번호 확인", description = "이메일 인증번호가 유효한지 확인합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "이메일 인증번호 확인 성공", content = @Content(schema = @Schema(implementation = RefreshResponse.class))),
            @ApiResponse(responseCode = "400", description = "인증정보 확인 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmailAuthenticationCodeResponse> checkEmailAuthenticationCode(@Valid @PathVariable String verifiedCode, HttpServletRequest request) {
        Boolean isValidEmail = emailSendUtil.getUserEmailByCode(verifiedCode);
        EmailAuthenticationCodeResponse emailAuthenticationCodeResponse = new EmailAuthenticationCodeResponse(isValidEmail);
        return ResponseEntity.ok().body(emailAuthenticationCodeResponse);
    }
}
