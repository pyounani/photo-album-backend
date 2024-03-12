package com.squarecross.photoalbum.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /**
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */

    ALBUMID_NOT_FOUND(HttpStatus.NOT_FOUND, "앨범 아이디가 존재하지 않습니다.")

    /**
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */

    /**
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */

    ;

    private final HttpStatus status;
    private final String message;

}
