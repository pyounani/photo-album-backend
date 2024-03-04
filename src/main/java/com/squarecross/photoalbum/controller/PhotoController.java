package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.ChangeAlbumDto;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
                // 단일 이미지 다운로드
                downloadSinglePhoto(photoIds[0], response);
            } else {
                // 다중 이미지를 zip 파일로 묶어서 내보내기
                downloadPhotosAsZip(photoIds, response);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing download request", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<PhotoDto>> getPhotoList(@PathVariable("albumId") Long albumId) {
        List<PhotoDto> res = photoService.getPhotoList(albumId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/move")
    public ResponseEntity<List<PhotoDto>> changeAlbumForPhoto(@RequestBody ChangeAlbumDto changeAlbumDto) {
        List<PhotoDto> photos = new ArrayList<>();
        for(Long photoId : changeAlbumDto.getPhotoIds()) {
            PhotoDto photoDto = photoService.changeAlbumForPhoto(changeAlbumDto.getToAlbumId(), photoId);
            photos.add(photoDto);
        }
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    private void downloadSinglePhoto(Long photoId, HttpServletResponse response) throws IOException {
        File file = photoService.getImageFile(photoId);
        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {

            // 파일을 복사하는 작업
            IOUtils.copy(fileInputStream, outputStream);
        }
    }

    private void downloadPhotosAsZip(Long[] photoIds, HttpServletResponse response) throws IOException {
        // Zip 파일 생성
        File zipFile = createZipFile(photoIds);

        // HTTP 응답 헤더 설정
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=photos.zip");

        try (FileInputStream fileInputStream = new FileInputStream(zipFile);
             OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(fileInputStream, outputStream);
        }

        if (!zipFile.delete()) {
            throw new IOException("임시 Zip 파일 생성 실패");
        }


    }

    private File createZipFile(Long[] photoIds) throws IOException {
        // 임시 디렉토리에 zip 파일 생성
        File zipFile = File.createTempFile("photos", ".zip");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (Long photoId : photoIds) {
                // 각 이미지 파일을 Zip 엔트리로 추가
                addPhotoToZip(photoId, zipOutputStream);
            }
        }

        return zipFile;
    }

    private void addPhotoToZip(Long photoId, ZipOutputStream zipOutputStream) throws IOException {
        File file = photoService.getImageFile(photoId);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            // Zip 엔트리 생성 및 데이터 복사
            ZipEntry zipEntry = new ZipEntry("photo_" + photoId + ".jpg");
            zipOutputStream.putNextEntry(zipEntry);
            IOUtils.copy(fileInputStream, zipOutputStream);
            zipOutputStream.closeEntry();
        }
    }

}
