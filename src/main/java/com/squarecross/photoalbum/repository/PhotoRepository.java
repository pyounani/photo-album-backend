package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Album;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {

    private final EntityManager em;

    public int countAlbum(Long albumId) {
        Long count = em.createQuery("SELECT COUNT(p) FROM Photo p WHERE p.album.id = :albumId", Long.class)
                .setParameter("albumId", albumId)
                .getSingleResult();
        return count.intValue();
    }
}
