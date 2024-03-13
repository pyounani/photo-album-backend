package com.squarecross.photoalbum.dto;

import com.squarecross.photoalbum.code.ResponseCode;
import lombok.Data;

@Data
public class ResponseDto<T> {
    private Integer status;
    private String code;
    private String message;
    private T data;

    public ResponseDto(ResponseCode responseCode, T data) {
        this.status = responseCode.getStatus().value();
        this.code = responseCode.name();
        this.message = responseCode.getMessage();
        this.data = data;
    }
}
