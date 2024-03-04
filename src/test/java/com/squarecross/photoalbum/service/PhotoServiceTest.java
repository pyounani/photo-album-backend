package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PhotoServiceTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired PhotoService photoService;

    @Test
    public void 사진상세정보_가져오기() throws Exception {
        Album album = new Album();
        album.setName("name");
        Long albumId = albumRepository.save(album);

        Photo photo = new Photo();
        photo.setAlbum(album);
        Photo savePhoto = photoRepository.save(photo);

        PhotoDto findPhoto = photoService.getPhoto(savePhoto.getId());

        assertEquals(albumId, findPhoto.getAlbumId());

    }

    @Test
    public void 사진_목록_불러오기() {
        Album album = new Album();
        album.setName("name");
        Long albumId = albumRepository.save(album);

        Photo photo1 = new Photo();
        photo1.setAlbum(album);
        photoRepository.save(photo1);

        Photo photo2 = new Photo();
        photo2.setAlbum(album);
        photoRepository.save(photo2);

        List<PhotoDto> photoList = photoService.getPhotoList(albumId);

        assertEquals(2, photoList.size());

    }

    @Test
    public void 앨범_바꾸기() {

        Album album1 = new Album();
        album1.setName("album1");
        albumRepository.save(album1);

        Album album2 = new Album();
        album2.setName("album2");
        Long newAlbumId = albumRepository.save(album2);

        Photo photo1 = new Photo();
        photo1.setAlbum(album1);
        Photo savePhoto = photoRepository.save(photo1);

        PhotoDto photoDto = photoService.changeAlbumForPhoto(newAlbumId, savePhoto.getId());

        assertEquals(newAlbumId, photoDto.getAlbumId());

    }


}