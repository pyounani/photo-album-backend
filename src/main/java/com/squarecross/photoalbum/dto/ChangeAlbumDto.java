package com.squarecross.photoalbum.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeAlbumDto {

    private Long fromAlbumId;
    private Long toAlbumId;

    private List<Long> photoIds;
}
