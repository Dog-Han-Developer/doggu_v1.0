package com.doghandeveloper.doggu.account.dto.request;

import com.doghandeveloper.doggu.account.domain.Account;
import com.doghandeveloper.doggu.account.domain.DogModel;
import com.doghandeveloper.doggu.account.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "회원 가입 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupRequest {

    @Schema(description = "회원 아이디", example = "abcde1234@gmail.com")
    @Length(max = 100, message = "아이디는 100자 이하로 입력해주세요.")
    @Email(message = "아이디는 이메일만 입력이 가능합니다.")
    @NotBlank
    String email;

    @Schema(description = "회원 닉네임", example = "lee_doggu")
    @Length(min = 2, max = 16, message = "닉네임은 2자이상 16자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9_.+]*$", message = "닉네임은 영어 소문자와 숫자,'_','.'만 입력이 가능합니다.")
    @NotBlank
    String userName;

    @Schema(description = "회원 비밀번호", example = "abcde1234")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9!@#$%^&+=]*$", message = "비밀번호는 영어 대소문자와 숫자, 특수문자만 입력이 가능합니다.")
    @NotBlank
    String password;

    public Account toAccount() {
        return Account.builder()
                .email(email)
                .userName(userName)
                .password(password)
                .role(UserRole.ROLE_USER)
                .dogModel(DogModel.NONE)
                .build();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
