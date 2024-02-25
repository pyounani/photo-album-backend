package com.squarecross.photoalbum.mapper;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;

public class AlbumMapper {
    public static AlbumDto convertToDto(Album album) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumId(album.getId());
        albumDto.setAlbumName(album.getName());
        albumDto.setCreatedAt(album.getCreatedAt());
        return albumDto;
    }

    public static Album convertToModel(AlbumDto albumDto) {
        Album album = new Album();
        album.setId(albumDto.getAlbumId());
        album.setName(albumDto.getAlbumName());
        album.setCreatedAt(albumDto.getCreatedAt());
        return album;
    }
}
