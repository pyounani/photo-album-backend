package com.squarecross.photoalbum.dto;

import com.squarecross.photoalbum.domain.Album;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AlbumDto {

    private Long albumId;
    private String albumName;
    private LocalDate createdAt;
    private int count;

}
