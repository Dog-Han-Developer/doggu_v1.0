package com.doghandeveloper.doggu.common.exception;

/**
 * com.mallang.healingmate.common.exception
 * AuthException.java
 * @date    2021-05-01 오후 8:40
 * @author  이아영
 *
 * @변경이력
 **/

public class AuthException extends BusinessException{
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
