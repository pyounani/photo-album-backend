package com.squarecross.photoalbum.dto;

import com.squarecross.photoalbum.domain.Album;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class AlbumDto {

    private Long albumId;
    private String albumName;
    private LocalDateTime createdAt;
    private int count;

}
