package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {

    private final EntityManager em;

    public Long save(Photo photo) {
        em.persist(photo);
        return photo.getId();
    }

    public int countAlbum(Long albumId) {
        try {
            Long count = em.createQuery("SELECT COUNT(p) FROM Photo p WHERE p.album.id = :albumId", Long.class)
                    .setParameter("albumId", albumId)
                    .getSingleResult();
            return count.intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public Photo findOne(Long photoId) {
        return em.find(Photo.class, photoId);
    }

    public Optional<Photo> findByFileNameAndAlbum_AlbumId(String photoName, Long albumId) {
        try {
            return Optional.ofNullable(em.createQuery(
                            "SELECT p FROM Photo p WHERE p.fileName = :photoName AND p.album.id = :albumId",
                            Photo.class)
                    .setParameter("photoName", photoName)
                    .setParameter("albumId", albumId)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
