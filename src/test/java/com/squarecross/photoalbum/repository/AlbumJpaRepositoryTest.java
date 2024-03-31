package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumJpaRepositoryTest {

    @Autowired
    AlbumJpaRepository albumJpaRepository;

    @Test
    public void 최신순_정렬() throws InterruptedException {
        Album album1 = new Album();
        album1.setName("aaaa");

        Album album2 = new Album();
        album2.setName("aaab");

        albumJpaRepository.save(album1);
        TimeUnit.SECONDS.sleep(1); //시간차를 벌리기위해 두번째 앨범 생성 1초 딜레이
        albumJpaRepository.save(album2);

        List<Album> findAlbumList = albumJpaRepository.findByAlbumNameContainingOrderByCreatedAtDesc("aaa");
        assertEquals("aaab", findAlbumList.get(0).getName());
        assertEquals("aaaa", findAlbumList.get(1).getName());
        assertEquals(2, findAlbumList.size());

    }

    @Test
    public void 앨범명_정렬() throws InterruptedException {
        Album album1 = new Album();
        album1.setName("aaaa");

        Album album2 = new Album();
        album2.setName("aaab");

        albumJpaRepository.save(album1);
        TimeUnit.SECONDS.sleep(1); //시간차를 벌리기위해 두번째 앨범 생성 1초 딜레이
        albumJpaRepository.save(album2);

        List<Album> findAlbumList = albumJpaRepository.findByAlbumNameContainingOrderByAlbumNameAsc("aaa");
        assertEquals("aaaa", findAlbumList.get(0).getName());
        assertEquals("aaab", findAlbumList.get(1).getName());
        assertEquals(2, findAlbumList.size());
    }

}