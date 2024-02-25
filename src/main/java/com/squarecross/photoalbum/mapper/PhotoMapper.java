package com.squarecross.photoalbum.mapper;

import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.PhotoDto;

public class PhotoMapper {

    public static PhotoDto convertToDto(Photo photo) {
        PhotoDto photoDto = new PhotoDto();
        photoDto.setPhotoId(photo.getId());
        photoDto.setFileName(photo.getFileName());
        photoDto.setFileSize(photo.getFileSize());
        photoDto.setOriginalUrl(photo.getOriginalUrl());
        photoDto.setThumbUrl(photo.getThumbUrl());
        photoDto.setUploadedAt(photo.getUploadedAt());
        photoDto.setAlbumId(photo.getAlbum().getId());
        return photoDto;
    }
}
