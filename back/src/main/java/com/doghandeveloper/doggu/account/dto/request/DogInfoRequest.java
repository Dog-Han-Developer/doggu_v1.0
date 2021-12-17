package com.doghandeveloper.doggu.account.dto.request;

import com.doghandeveloper.doggu.account.domain.Account;
import com.doghandeveloper.doggu.account.domain.Dog;
import com.doghandeveloper.doggu.account.domain.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Schema(description = "강아지 정보 저장 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DogInfoRequest {

    @Schema(description = "강아지 이름", example = "보미")
    @Length(max = 100, message = "강아지 이름은 100자 이하로 입력해주세요.")
    @Pattern(regexp = "^[가-힣\\s]*$", message = "강아지 이름은 한글, 띄어쓰기만 가능합니다.")
    @NotBlank
    String dogName;

    @Schema(description = "강아지 종류", example = "웰시코기")
    @Length(max = 100, message = "강아지 종류는 100자 이하로 입력해주세요.")
    @Pattern(regexp = "^[가-힣\\s]*$", message = "강아지 종류는 한글, 띄어쓰기만 가능합니다.")
    @NotBlank
    String dogKind;

    @Schema(description = "강아지 몸무게(kg)", example = "10kg")
    @Max(value = 100)
    @Min(value = 0)
    @NotNull
    Integer dogWeight;

    @Schema(description = "강아지 생년월일", example = "2012-01-22")
    @Length(min = 10, max = 10, message = "생년월일은 -포함 10자로 입력해주세요.")
    @Pattern(regexp = "^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$", message = "YYYY-MM-DD의 형태만 가능합니다.")
    @NotBlank
    String dogBirth;

    @Schema(description = "강아지 성별(M,F)", example = "M 또는 F")
    @Pattern(regexp = "M|F", message = "강아지 성별은 M,F만 가능합니다.")
    @NotBlank
    String dogSex;

    public Dog toDog(Account account) {
        return Dog.builder()
                .name(dogName)
                .kind(dogKind)
                .weight(dogWeight)
                .birth(dogBirth)
                .sex(Sex.valueOf(dogSex))
                .account(account)
                .build();
    }
}
