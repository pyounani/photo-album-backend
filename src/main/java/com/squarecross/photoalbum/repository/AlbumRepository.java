package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlbumRepository {

    private final EntityManager em;

    // 저장
    public Long save(Album album) {
        em.persist(album);
        em.flush();
        return album.getId();
    }

    // 삭제
    public void delete(Long id) {
        Album findAlbum = em.find(Album.class, id);
        if(findAlbum != null) {
            em.remove(findAlbum);
        }
    }

    // 한 개만 조회
    public Album findOne(Long id) {
        return em.find(Album.class, id);
    }

    // 목록 조회
    public List<Album> findAll() {
        return em.createQuery("select a from Album a", Album.class)
                .getResultList();
    }

}
