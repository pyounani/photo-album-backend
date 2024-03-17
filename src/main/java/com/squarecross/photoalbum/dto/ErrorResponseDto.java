package com.squarecross.photoalbum.dto;

import com.squarecross.photoalbum.code.ErrorCode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String code;
    private String message;

    public ErrorResponseDto(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
