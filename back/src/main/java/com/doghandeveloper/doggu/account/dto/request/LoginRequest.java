package com.doghandeveloper.doggu.account.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "로그인 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {
    @Schema(description = "회원 아이디", example = "abcde1234@gmail.com")
    @Length(max = 100, message = "아이디는 100자 이하로 입력해주세요.")
    @Email(message = "아이디는 이메일만 입력이 가능합니다.")
    @NotBlank
    private String email;

    @Schema(description = "회원 비밀번호", example = "abcde1234")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9!@#$%^&+=]*$", message = "비밀번호는 영어 대소문자와 숫자, 특수문자만 입력이 가능합니다.")
    @NotBlank
    private String password;
}
