package com.squarecross.photoalbum.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PhotoDetailsDto {
    private Long photoId;
    private String fileName;
    private String originalUrl;
    private LocalDateTime uploadedAt;
    private int fileSize;
}
