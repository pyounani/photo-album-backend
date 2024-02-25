package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.Constants;
import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumServiceTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired AlbumService albumService;

    private Long albumId;

    @Test
    public void 앨범_조회() throws Exception {
        Album album = new Album();
        album.setName("name");
        Long albumId = albumRepository.save(album);

        AlbumDto findAlbum = albumService.getAlbum(albumId);

        assertEquals("name", findAlbum.getAlbumName());
    }

    @Test()
    public void 존재하지_않는_앨범조회() throws Exception{
        assertThrows(IllegalStateException.class, () -> {
            albumService.getAlbum(100L);
        });
    }

    @Test
    public void 사진갯수_조회() throws Exception {
        Album album = new Album();
        album.setName("name");
        Long albumId = albumRepository.save(album);

        Photo photo = new Photo();
        photo.setAlbum(album);
        photoRepository.save(photo);

        AlbumDto findAlbumDto = albumService.getAlbum(albumId);
        assertEquals(1, findAlbumDto.getCount());
    }

    @Test
    public void 앨범_생성() throws Exception {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("name");
        AlbumDto findAlbumDto = albumService.createAlbum(albumDto);

        Album findAlbum = albumRepository.findOne(findAlbumDto.getAlbumId());
        albumId = findAlbumDto.getAlbumId();

        assertEquals("name", findAlbum.getName());
    }

    @AfterEach
    private void cleanupAlbumDirectories() throws IOException {
        if(albumId != null) {
            Path originalPath = Paths.get(Constants.PATH_PREFIX + "/photos/original/" + albumId);
            Path thumbPath = Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + albumId);

            try {
                Files.deleteIfExists(originalPath);
                Files.deleteIfExists(thumbPath);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

}