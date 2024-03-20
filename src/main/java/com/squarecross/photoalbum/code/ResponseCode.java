package com.squarecross.photoalbum.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    /**
     * Album
     */
    SUCCESS_GET_ALBUM(HttpStatus.OK, "앨범 기본 정보를 성공적으로 가져왔습니다."),
    SUCCESS_POST_ALBUM(HttpStatus.OK, "앨범을 성공적으로 생성했습니다."),
    SUCCESS_PUT_ALBUM(HttpStatus.OK, "앨범명을 성공적으로 변경했습니다."),
    SUCCESS_DELETE_ALBUM(HttpStatus.OK, "앨범을 성공적으로 삭제했습니다."),
    SUCCESS_GET_ALBUM_LIST(HttpStatus.OK, "앨범 목록을 성공적으로 가져왔습니다."),

    /**
     * Photo
     */
    SUCCESS_GET_PHOTO(HttpStatus.OK, "사진 기본 정보를 성공적으로 가져왔습니다."),
    SUCCESS_POST_PHOTO(HttpStatus.OK, "사진을 성공적으로 생성했습니다."),
    SUCCESS_PUT_PHOTO(HttpStatus.OK, "사진을 원하시는 앨범에 옮기기를 성공적으로 변경했습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
