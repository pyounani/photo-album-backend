package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.Constants;
import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.dto.PhotoDetailsDto;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PhotoServiceTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired PhotoService photoService;
    @Autowired AlbumService albumService;

    private Long albumId;
    private Long photoId;

    @Test
    public void 사진상세정보_가져오기() throws Exception {
        Album album = new Album();
        album.setName("name");
        Album savedAlbum = albumRepository.save(album);

        Photo photo = new Photo();
        photo.setAlbum(album);
        Photo savedPhoto = photoRepository.save(photo);

        PhotoDetailsDto findPhoto = photoService.getPhoto(savedAlbum.getId(), savedPhoto.getId());

        assertEquals(savedPhoto.getId(), findPhoto.getPhotoId());
    }

    @Test
    public void 사진_생성() throws Exception {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("name");
        AlbumDto findAlbumDto = albumService.createAlbum(albumDto);
        albumId = findAlbumDto.getAlbumId();

        MockMultipartFile multipartFile = makeImageFile();

        PhotoDto photoDto = photoService.savePhoto(multipartFile, albumId);
        photoId = photoDto.getPhotoId();

        assertEquals(multipartFile.getName(), photoDto.getFileName());
    }

    @Test
    public void 앨범_바꾸기() {

        Album album1 = new Album();
        album1.setName("album1");
        Album fromAlbum = albumRepository.save(album1);

        Album album2 = new Album();
        album2.setName("album2");
        Album newAlbum = albumRepository.save(album2);

        Photo photo1 = new Photo();
        photo1.setAlbum(album1);
        Photo savedPhoto = photoRepository.save(photo1);

        photoService.changeAlbumForPhoto(fromAlbum.getId(), newAlbum.getId(), savedPhoto.getId());

        Optional<Photo> findPhoto = photoRepository.findById(savedPhoto.getId());

        assertEquals(newAlbum.getId(), findPhoto.get().getAlbum().getId());

    }

    @Test
    public void 앨범_삭제() throws IOException {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("name");
        AlbumDto findAlbumDto = albumService.createAlbum(albumDto);
        albumId = findAlbumDto.getAlbumId();

        MockMultipartFile multipartFile1 = makeImageFile();

        PhotoDto photoDto1 = photoService.savePhoto(multipartFile1, albumId);

        List<Long> photoIds = new ArrayList<>();
        photoIds.add(photoDto1.getPhotoId());

        List<PhotoDto> photos = photoService.deletePhoto(albumId, photoIds);

        assertEquals(0, photos.size());
        assertFalse(photos.contains(photoDto1));
    }

    @Test
    public void 일부_앨범_삭제() throws IOException{
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("name");
        AlbumDto findAlbumDto = albumService.createAlbum(albumDto);
        albumId = findAlbumDto.getAlbumId();

        MockMultipartFile multipartFile1 = makeImageFile();
        PhotoDto photoDto1 = photoService.savePhoto(multipartFile1, albumId);
        photoId = photoDto1.getPhotoId();

        MockMultipartFile multipartFile2 = makeImageFile();
        PhotoDto photoDto2 = photoService.savePhoto(multipartFile2, albumId);

        List<Long> photoIds = new ArrayList<>();
        photoIds.add(photoDto2.getPhotoId());

        List<PhotoDto> photos = photoService.deletePhoto(albumId, photoIds);

        assertEquals(1, photos.size());
    }

    @AfterEach
    private void cleanupFiles() throws IOException {
        if (photoId != null) {
            Path originalPath = Paths.get(Constants.PATH_PREFIX + "/photos/original/" + albumId + "/image.JPG");
            Path thumbPath = Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + albumId + "/image.JPG");
            try {
                Files.deleteIfExists(originalPath);
                Files.deleteIfExists(thumbPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(albumId != null) {
            Path originalAlbumPath = Paths.get(Constants.PATH_PREFIX + "/photos/original/" + albumId);
            Path thumbAlbumPath = Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + albumId);
            try {
                Files.deleteIfExists(originalAlbumPath);
                Files.deleteIfExists(thumbAlbumPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MockMultipartFile makeImageFile() throws IOException {
        // 이미지 파일 경로
        String imagePath = "src/main/resources/static/image.JPG";

        // 이미지 파일을 바이트 배열로 읽기
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

        // 이미지 파일 이름
        String fileName = "image.JPG";

        // 이미지 파일의 MIME 타입
        String contentType = "image/jpeg";

        // MockMultipartFile 생성하여 반환
        return new MockMultipartFile(fileName, fileName, contentType, imageBytes);
    }

}