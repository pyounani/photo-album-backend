package com.squarecross.photoalbum.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PhotoDto {
    private Long photoId;
    private String fileName;
    private String thumbUrl;
    private LocalDateTime uploadedAt;

}
