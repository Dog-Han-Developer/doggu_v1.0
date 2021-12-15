package com.doghandeveloper.doggu.account.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "이메일 인증 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailAuthenticationRequest {

    @Schema(description = "인증 요청 이메일", example = "abcde1234@gmail.com")
    @Length(max = 100, message = "이메일 길이는 100자를 넘어갈 수 없습니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank
    String email;
}
