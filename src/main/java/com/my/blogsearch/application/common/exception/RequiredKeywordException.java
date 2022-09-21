package com.my.blogsearch.application.common.exception;

import com.my.blogsearch.application.common.ApiErrorCode;
import lombok.Getter;

@Getter
public class RequiredKeywordException extends RuntimeException {
    private final Integer code;
    private final String message;

    public RequiredKeywordException() {
        this.code = ApiErrorCode.REQUIRED_PARAM_KEYWORD.getCode();
        this.message = ApiErrorCode.REQUIRED_PARAM_KEYWORD.getMessage();
    }
}
