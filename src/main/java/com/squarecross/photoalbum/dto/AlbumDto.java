package com.squarecross.photoalbum.dto;

import com.squarecross.photoalbum.domain.Album;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AlbumDto {

    private Long albumId;
    private String albumName;
    private LocalDate createdAt;
    private int count;

    public static AlbumDto createAlbumDto(Album album) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.albumId = album.getId();
        albumDto.albumName = album.getName();
        albumDto.createdAt = album.getCreatedAt();
        albumDto.count = (album.getPhotos() != null) ? album.getPhotos().size() : 0;
        return albumDto;
    }

}
