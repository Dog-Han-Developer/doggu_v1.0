package com.doghandeveloper.doggu.account.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Schema(description ="강아지 인증 요청")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DogRegistrationRequest {

    @Schema(description = "동물등록번호",example = "410160010953287")
    @NotBlank
    String registerNumber;

    @Schema(description = "견주 등록 이름",example = "이수민")
    String ownerName;
}
