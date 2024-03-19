package com.squarecross.photoalbum.exception;

import com.squarecross.photoalbum.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileExtensionMissingException extends RuntimeException {
    private final ErrorCode errorCode;
}
