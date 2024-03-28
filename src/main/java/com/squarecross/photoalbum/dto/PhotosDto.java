package com.squarecross.photoalbum.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhotosDto {
    private Long albumId;

    private List<Long> photoIds;
}
