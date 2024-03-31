package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AlbumRepository {

    private final EntityManager em;

    // 저장
    public Long save(Album album) {
        em.persist(album);
        return album.getId();
    }

    // 삭제
    public void delete(Long id) {
        Album findAlbum = em.find(Album.class, id);
        if (findAlbum != null) {
            em.remove(findAlbum);
        }
    }

    // 한 개만 조회
    public Optional<Album> findOne(Long id) {
        Album album = em.find(Album.class, id);
        return Optional.ofNullable(album);
    }

    // 목록 조회
    public List<Album> findAll() {
        return em.createQuery("select a from Album a", Album.class)
                .getResultList();
    }

    public List<Album> findByAlbumNameContainingOrderByCreatedAtDesc(String keyword) {
        String jpql = "SELECT a FROM Album a WHERE LOWER(a.name) LIKE LOWER(:keyword) ORDER BY a.createdAt DESC";
        TypedQuery<Album> query = em.createQuery(jpql, Album.class);
        query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }

    public List<Album> findByAlbumNameContainingOrderByAlbumNameAsc(String keyword) {
        String jpql = "SELECT a FROM Album a WHERE LOWER(a.name) LIKE LOWER(:keyword) ORDER BY a.name ASC";
        TypedQuery<Album> query = em.createQuery(jpql, Album.class);
        query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }

}
