package com.doghandeveloper.doggu.common.exception;

public class EntityException extends BusinessException{
    public EntityException(ErrorCode errorCode) {
        super(errorCode);
    }
}