package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/albums/{albumId}/photos")
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoDto> getPhotoInfo(@PathVariable("albumId") Long albumId,
                                                 @PathVariable("photoId") Long photoId) {
        PhotoDto res = photoService.getPhoto(photoId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<List<PhotoDto>> uploadPhotos(@PathVariable("albumId") Long albumId,
//                                                       @PathVariable("photoId") Long photoId) {
//
//    }

}
