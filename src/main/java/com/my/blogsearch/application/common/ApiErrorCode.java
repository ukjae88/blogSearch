package com.my.blogsearch.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiErrorCode {
    REQUIRED_PARAM_KEYWORD(1001, "키워드 파라미터 필요"),
    INVALID_PARAMETER(1002, "유효하지 않은 파라미터 오류"),
    ;

    private final int code;
    private final String message;
}
