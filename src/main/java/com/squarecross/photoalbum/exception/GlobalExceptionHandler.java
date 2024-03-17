package com.squarecross.photoalbum.exception;

import com.squarecross.photoalbum.code.ErrorCode;
import com.squarecross.photoalbum.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //컨트롤러 전역에서 발생하는 예외 throw
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Album
     */

    @ExceptionHandler(AlbumIdNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleAlbumIdNotFoundException(final AlbumIdNotFoundException e) {
        log.error("handleAlbumIdNotFoundException : {}", e.getErrorCode().getMessage());
        return ResponseEntity
                .status(ErrorCode.ALBUMID_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDto(ErrorCode.ALBUMID_NOT_FOUND));
    }

    @ExceptionHandler(UnknownSortingException.class)
    protected ResponseEntity<ErrorResponseDto> handleUnknownSortingException(final UnknownSortingException e) {
        log.error("handleUnknownSortingException : {}", e.getErrorCode().getMessage());
        return ResponseEntity
                .status(ErrorCode.SORT_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDto(ErrorCode.SORT_NOT_FOUND));
    }

    /**
     * Photo
     */

    @ExceptionHandler(PhotoIdNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handlePhotoIdNotFoundException(final PhotoIdNotFoundException e) {
        log.error("handlePhotoIdNotFoundException : {}", e.getErrorCode().getMessage());
        return ResponseEntity
                .status(ErrorCode.PHOTOID_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDto(ErrorCode.PHOTOID_NOT_FOUND));
    }

    @ExceptionHandler(AlbumIdMismatchException.class)
    protected ResponseEntity<ErrorResponseDto> handleAlbumIdMismatchException(final AlbumIdMismatchException e) {
        log.error("handleAlbumIdMismatchException : {}", e.getErrorCode().getMessage());
        return ResponseEntity
                .status(ErrorCode.ALBUMID_MISMATCH.getStatus().value())
                .body(new ErrorResponseDto(ErrorCode.ALBUMID_MISMATCH));
    }

}
