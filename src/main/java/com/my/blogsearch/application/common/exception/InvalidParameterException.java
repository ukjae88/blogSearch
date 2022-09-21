package com.my.blogsearch.application.common.exception;

import com.my.blogsearch.application.common.ApiErrorCode;
import lombok.Getter;

@Getter
public class InvalidParameterException extends RuntimeException {
    private final Integer code;
    private final String message;

    public InvalidParameterException() {
        this.code = ApiErrorCode.INVALID_PARAMETER.getCode();
        this.message = ApiErrorCode.INVALID_PARAMETER.getMessage();
    }
}
