package com.my.blogsearch.application.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    Integer code;
    String message;
    Object result;

    public static ApiResponse error(Integer code, String message) {
        return ApiResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

    public static ApiResponse success(Object result) {
        return ApiResponse.builder()
                .result(result)
                .build();
    }
}
