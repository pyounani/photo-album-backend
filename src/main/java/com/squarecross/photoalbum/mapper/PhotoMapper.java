package com.squarecross.photoalbum.mapper;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.dto.PhotoDetailsDto;
import com.squarecross.photoalbum.dto.PhotoDto;

import java.util.List;
import java.util.stream.Collectors;

public class PhotoMapper {
    public static PhotoDto convertToDto(Photo photo) {
        PhotoDto photoDto = new PhotoDto();
        photoDto.setPhotoId(photo.getId());
        photoDto.setFileName(photo.getFileName());
        photoDto.setThumbUrl(photo.getThumbUrl());
        photoDto.setUploadedAt(photo.getUploadedAt());
        return photoDto;
    }

    public static PhotoDetailsDto convertToDetailsDto(Photo photo) {
        PhotoDetailsDto photoDetailsDto = new PhotoDetailsDto();
        photoDetailsDto.setPhotoId(photo.getId());
        photoDetailsDto.setFileName(photo.getFileName());
        photoDetailsDto.setOriginalUrl(photo.getOriginalUrl());
        photoDetailsDto.setUploadedAt(photo.getUploadedAt());
        photoDetailsDto.setFileSize(photo.getFileSize());
        return photoDetailsDto;
    }

    public static List<PhotoDto> convertToDtoList(List<Photo> photos) {
        return photos.stream().map(PhotoMapper::convertToDto).collect(Collectors.toList());
    }
}
