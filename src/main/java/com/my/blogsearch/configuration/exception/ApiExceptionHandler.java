package com.my.blogsearch.configuration.exception;

import com.my.blogsearch.application.common.ApiResponse;
import com.my.blogsearch.application.common.exception.InvalidParameterException;
import com.my.blogsearch.application.common.exception.RequiredKeywordException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RequiredKeywordException.class)
    public ResponseEntity<ApiResponse> handleRequiredKeywordException(RequiredKeywordException e) {
        return ResponseEntity.internalServerError().body(ApiResponse.error(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiResponse> handleInvalidParameterException(InvalidParameterException e) {
        return ResponseEntity.internalServerError().body(ApiResponse.error(e.getCode(), e.getMessage()));
    }
}
