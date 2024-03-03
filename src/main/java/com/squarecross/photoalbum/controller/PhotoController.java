package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
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

    @PostMapping
    public ResponseEntity<List<PhotoDto>> uploadPhotos(@PathVariable("albumId") Long albumId,
                                                       @RequestParam("photos") MultipartFile[] files) throws IOException {

        List<PhotoDto> photos = new ArrayList<>();
        for (MultipartFile file : files) {
            PhotoDto photoDto = photoService.savePhoto(file, albumId);
            photos.add(photoDto);
        }

        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @GetMapping("/download")
    public void downloadPhotos(@RequestParam("photoIds") Long[] photoIds, HttpServletResponse response) {
        try {
            if (photoIds.length == 1) {
                File file = photoService.getImageFile(photoIds[0]);
                try (FileInputStream fileInputStream = new FileInputStream(file);
                     OutputStream outputStream = response.getOutputStream()) {

                    // 파일을 복사하는 작업
                    IOUtils.copy(fileInputStream, outputStream);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        } catch (IOException e) {
            throw new RuntimeException("Error copying file", e);
        }
    }

}
