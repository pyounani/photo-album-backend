package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PhotoRepositoryTest {

    @Autowired PhotoRepository photoRepository;
    @Autowired AlbumRepository albumRepository;

    @Test
    public void countAlbum() {
        Album album = new Album();
        album.setName("name");
        Long albumId = albumRepository.save(album);

        Photo photo1 = new Photo();
        photo1.setAlbum(album);
        photoRepository.save(photo1);

        Photo photo2 = new Photo();
        photo2.setAlbum(album);
        photoRepository.save(photo2);

        assertEquals(2, photoRepository.countAlbum(albumId));
    }

}