package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.Constants;
import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.mapper.PhotoMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final AlbumRepository albumRepository;

    // 원본 이미지와 썸네일을 저장할 경로 설정
    private final String original_path = Constants.PATH_PREFIX + "/photos/original";
    private final String thumb_path = Constants.PATH_PREFIX + "/photos/thumb";

    public PhotoDto getPhoto(Long photoId) {
        // 사진 정보를 찾아오기
        Optional<Photo> findPhoto = photoRepository.findOne(photoId);
        if (findPhoto.isEmpty()) {
            // 사진이 없으면 EntityNotFoundException 발생
            throw new EntityNotFoundException();
        }
        // DTO로 변환하여 반환
        return PhotoMapper.convertToDto(findPhoto.get());
    }

    public List<PhotoDto> getPhotoList(Long albumId) {
        List<Photo> findPhotoList = photoRepository.findByAlbum(albumId);
        return findPhotoList.stream()
                .map(PhotoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public PhotoDto savePhoto(MultipartFile file, Long albumId) throws IOException {
        // 앨범 정보 조회
        Album findAlbum = albumRepository.findOne(albumId);
        if (findAlbum == null) {
            // 앨범이 없으면 EntityNotFoundException 발생
            throw new EntityNotFoundException();
        }

        // 파일 정보 추출
        String fileName = file.getOriginalFilename();
        int fileSize = (int) file.getSize();

        // 중복 처리된 파일 이름 가져오기
        fileName = getNextFileName(fileName, albumId);

        // 파일 저장
        saveFile(file, albumId, fileName);

        // Photo 엔티티 생성 및 저장
        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setOriginalUrl("/photos/original/" + albumId + "/" + fileName);
        photo.setThumbUrl("/photos/thumb/" + albumId + "/" + fileName);
        photo.setFileSize(fileSize);
        photo.setAlbum(findAlbum);
        Photo createdPhoto = photoRepository.save(photo);

        // 저장된 Photo를 DTO로 변환하여 반환
        return PhotoMapper.convertToDto(createdPhoto);
    }

    public File getImageFile(Long photoId) {
        // 사진 정보 조회
        Optional<Photo> findPhoto = photoRepository.findOne(photoId);
        if (findPhoto.isEmpty()) {
            // 사진이 없으면 EntityNotFoundException 발생
            throw new EntityNotFoundException();
        }
        // 파일 경로 생성 및 반환
        return new File(Constants.PATH_PREFIX + findPhoto.get().getOriginalUrl());
    }

    private String getNextFileName(String fileName, Long albumId) {
        String fileNameNoExt = StringUtils.stripFilenameExtension(fileName);
        String ext = StringUtils.getFilenameExtension(fileName);

        // 중복된 파일 이름 검사
        Optional<Photo> res = photoRepository.findByFileNameAndAlbum_AlbumId(fileName, albumId);

        int count = 2;
        while (res.isPresent()) {
            // 중복된 경우 숫자를 더해 새로운 파일 이름 생성
            fileName = String.format("%s (%d).%s", fileNameNoExt, count, ext);
            res = photoRepository.findByFileNameAndAlbum_AlbumId(fileName, albumId);
            count++;
        }
        return fileName;
    }

    private void saveFile(MultipartFile file, Long albumId, String fileName) throws IOException {
        String filePath = albumId + "/" + fileName;

        // 원본 이미지 저장
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Paths.get(original_path + "/" + filePath));
        } catch (IOException e) {
            // 파일 복사 중 에러 발생 시 RuntimeException 발생
            throw new RuntimeException("Error copying file", e);
        }

        // 썸네일 생성 및 저장
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage thumbImg = Scalr.resize(ImageIO.read(inputStream), Constants.THUMB_SIZE, Constants.THUMB_SIZE);
            File thumbFile = new File(thumb_path + "/" + filePath);
            String ext = StringUtils.getFilenameExtension(fileName);
            if (ext == null) {
                // 파일 확장자가 null일 경우 IllegalArgumentException 발생
                throw new IllegalArgumentException("File extension is null");
            }
            ImageIO.write(thumbImg, ext, thumbFile);
        } catch (Exception e) {
            // 썸네일 생성 중 에러 발생 시 RuntimeException 발생
            throw new RuntimeException("Error creating thumbnail", e);
        }
    }
}
