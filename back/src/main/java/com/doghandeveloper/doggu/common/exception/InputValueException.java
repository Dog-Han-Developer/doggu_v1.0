package com.doghandeveloper.doggu.common.exception;

public class InputValueException extends BusinessException {
    public InputValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
