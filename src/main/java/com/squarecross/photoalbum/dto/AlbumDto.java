package com.squarecross.photoalbum.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AlbumDto {
    private Long albumId;
    private String albumName;
    private LocalDate createdAt;
    private int count;

    public static AlbumDto createAlbumDto(Long albumId, String albumName, LocalDate createdAt, int count) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.albumId = albumId;
        albumDto.albumName = albumName;
        albumDto.createdAt = createdAt;
        albumDto.count = count;
        return albumDto;
    }
}
