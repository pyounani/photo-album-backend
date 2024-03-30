package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Test
    public void 최신_이미지_불러오기() {
        Album album = new Album();
        album.setName("name");
        albumRepository.save(album);

        Long photo1 = savePhoto(album);
        Long photo2 = savePhoto(album);
        Long photo3 = savePhoto(album);
        Long photo4 = savePhoto(album);
        Long photo5 = savePhoto(album);

        List<Photo> findList = photoRepository.findTop4ByAlbum_AlbumIdOrderByUploadedAtDesc(album.getId());

        assertEquals(photo5, findList.get(0).getId());
        assertEquals(photo4, findList.get(1).getId());
        assertEquals(photo3, findList.get(2).getId());
        assertEquals(photo2, findList.get(3).getId());

        assertEquals(4, findList.size());
    }

    private Long savePhoto(Album album) {
        Photo photo = new Photo();
        photo.setAlbum(album);
        photoRepository.save(photo);
        return photo.getId();
    }

    @Test
    public void 최신순_정렬() throws InterruptedException {
        Album album = new Album();
        album.setName("album");
        Long albumId = albumRepository.save(album);

        Photo photo1 = new Photo();
        photo1.setFileName("aaaa");
        photo1.setAlbum(album);

        Photo photo2 = new Photo();
        photo2.setFileName("aaab");
        photo2.setAlbum(album);

        photoRepository.save(photo1);
        TimeUnit.SECONDS.sleep(1); //시간차를 벌리기위해 두번째 앨범 생성 1초 딜레이
        photoRepository.save(photo2);

        List<Photo> findPhotoList = photoRepository.findByPhotoNameContainingAndAlbumIdOrderByCreatedAtDesc("aaa", albumId);
        assertEquals("aaab", findPhotoList.get(0).getFileName());
        assertEquals("aaaa", findPhotoList.get(1).getFileName());
        assertEquals(2, findPhotoList.size());
    }

    @Test
    public void 사진명_정렬() throws InterruptedException {
        Album album = new Album();
        album.setName("album");
        Long albumId = albumRepository.save(album);

        Photo photo1 = new Photo();
        photo1.setFileName("aaaa");
        photo1.setAlbum(album);

        Photo photo2 = new Photo();
        photo2.setFileName("aaab");
        photo2.setAlbum(album);

        photoRepository.save(photo1);
        TimeUnit.SECONDS.sleep(1); //시간차를 벌리기위해 두번째 앨범 생성 1초 딜레이
        photoRepository.save(photo2);

        List<Photo> findPhotoList = photoRepository.findByPhotoNameContainingAndAlbumIdOrderByPhotoNameAsc("aaa", albumId);
        assertEquals("aaaa", findPhotoList.get(0).getFileName());
        assertEquals("aaab", findPhotoList.get(1).getFileName());
        assertEquals(2, findPhotoList.size());
    }

}