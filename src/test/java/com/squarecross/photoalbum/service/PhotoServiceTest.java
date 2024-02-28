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
        Long photoId = photoRepository.save(photo);

        PhotoDto findPhoto = photoService.getPhoto(photoId);

        assertEquals(albumId, findPhoto.getAlbumId());

    }

}