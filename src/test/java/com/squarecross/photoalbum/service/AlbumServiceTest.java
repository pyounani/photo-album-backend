package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.Constants;
import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.exception.AlbumIdNotFoundException;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

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

        AlbumDto savedAlbum = albumService.getAlbum(albumId);

        Optional<Album> findAlbum = albumRepository.findOne(albumId);

        assertEquals(savedAlbum.getAlbumId(), findAlbum.get().getId());
    }

    @Test()
    public void 존재하지_않는_앨범조회() throws Exception{
        assertThrows(AlbumIdNotFoundException.class, () -> {
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

        Optional<Album> findAlbum = albumRepository.findOne(findAlbumDto.getAlbumId());
        albumId = findAlbumDto.getAlbumId();

        assertEquals("name", findAlbum.get().getName());
    }

    @Test
    public void 앨범명_변경() throws Exception {
        Album album = new Album();
        album.setName("name");
        Long albumId = albumRepository.save(album);

        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("changeName");

        albumService.changeAlbumName(albumId, albumDto);

        Optional<Album> findAlbum = albumRepository.findOne(albumId);

        assertEquals("changeName", findAlbum.get().getName());
    }

    @Test
    public void 앨범_삭제() throws Exception {

        List<Album> albumList = albumRepository.findAll();

        AlbumDto albumDto = new AlbumDto();
        albumDto.setAlbumName("name");
        AlbumDto findAlbumDto = albumService.createAlbum(albumDto);

        albumService.deleteAlbum(findAlbumDto.getAlbumId());

        List<Album> deleteAlbumList = albumRepository.findAll();

        assertEquals(albumList.size(), deleteAlbumList.size());
    }

    @Test
    public void 앨범_목록_불러오기() throws Exception {
        Album album1 = new Album();
        album1.setName("aaaa");

        Album album2 = new Album();
        album2.setName("aaab");

        albumRepository.save(album1);
        albumRepository.save(album2);

        Photo photo = new Photo();
        photo.setThumbUrl("/url");
        photo.setAlbum(album1);
        photoRepository.save(photo);

        List<AlbumDto> albums;
        albums = albumService.getAlbumList("aaa", "byName");

        assertEquals("aaaa", albums.get(0).getAlbumName());
        assertEquals("aaab", albums.get(1).getAlbumName());
        assertEquals("C:/youna/photo-album-backend/url", albums.get(0).getThumbUrls().get(0));
        assertEquals(2, albums.size());

        albums = albumService.getAlbumList("aaa", "byDate");
        assertEquals("aaab", albums.get(0).getAlbumName());
        assertEquals("aaaa", albums.get(1).getAlbumName());
        assertEquals(2, albums.size());

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