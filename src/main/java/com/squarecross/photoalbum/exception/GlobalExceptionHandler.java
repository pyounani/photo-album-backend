package com.squarecross.photoalbum.exception;

import com.squarecross.photoalbum.code.ErrorCode;
import com.squarecross.photoalbum.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //컨트롤러 전역에서 발생하는 예외 throw
public class GlobalExceptionHandler {

    /**
     * Album
     */

    @ExceptionHandler(AlbumIdNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleAlbumIdNotFoundException(final AlbumIdNotFoundException e) {
        return ResponseEntity
                .status(ErrorCode.ALBUMID_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDto(ErrorCode.ALBUMID_NOT_FOUND));
    }

}
