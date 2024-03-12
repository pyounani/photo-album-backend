package com.squarecross.photoalbum.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    SUCCESS_GET_ALBUM(HttpStatus.OK, "앨범 기본 정보를 성공적으로 가져왔습니다."),

    ;

    private final HttpStatus status;
    private final String message;
}