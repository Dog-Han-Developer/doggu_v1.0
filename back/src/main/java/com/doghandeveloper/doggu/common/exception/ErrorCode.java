package com.doghandeveloper.doggu.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "S_001", "서버가 올바르게 동작하지 않습니다."),
    METHOD_NOT_ALLOWED(405, "S_002", "지원하지 않는 HTTP method메소드입니다."),
    INVALID_INPUT_VALUE(400, "S_003", "적절하지 않은 요청 값입니다."),
    INVALID_TYPE_VALUE(400, "S_004", "요청 값의 타입이 잘못되었습니다."),

    AUTHENTICATION_ERROR(401, "AU_001", "인증 관련 오류가 발생했습니다."),
    AUTHORIZATION_ERROR(403,"AU_002","권한 관련 오류가 발생했습니다."),
    DUPLICATED_USER_ID(409, "AU_003", "이미 존재하는 아이디입니다."),
    DUPLICATED_NICKNAME(409, "AU_004", "이미 존재하는 닉네임입니다."),
    BAD_LOGIN(400, "AU_005", "올바르지 않은 아이디 또는 패스워드입니다."),
    BAD_REFRESH(400,"AU_006","올바르지 않은 Refresh Token 입니다."),
    INCORRECT_PASSWORD(400,"AU_007","비밀번호가 올바르지 않습니다."),
    ACCOUNT_NOT_FOUND(400,"AU_008","회원을 찾을 수 없습니다."),

    INCORRECT_KEYWORD(400,"KW_001","올바르지 않은 키워드가 있습니다."),
    INCORRECT_KEYWORD_LENGTH(400,"KW_002","키워드는 최대 3개까지 입력 가능합니다."),

    ENTITY_NOT_FOUND(404, "EN_001", "Entity를 찾을 수 없습니다"),
    DUPLICATED_ENTITY(409, "EN_002", "이미 존재하는 Entity입니다"),
    UNAUTHORIZED_ACCOUNT(403, "EN_003", "해당 Entity를 수정 또는 삭제할 권한이 없습니다"),

    INCORRECT_IMAGE_LENGTH(400, "IMG_001", "이미지는 최대 5개까지 입력 가능합니다."),

    EMAIL_SEND_FAIL_ERROR(500, "ES_001", "Email 송신에 실패하였습니다");
    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
