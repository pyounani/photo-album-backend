package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    int countByAlbum_Id(Long albumId);

    List<Photo> findTop4ByAlbum_IdOrderByUploadedAtDesc(Long albumId);

    List<Photo> findByFileNameContainingAndAlbum_IdOrderByUploadedAtDesc(String keyword, Long albumId);

    List<Photo> findByFileNameContainingAndAlbum_IdOrderByFileNameAsc(String keyword, Long albumId);

    List<Photo> findByAlbum_Id(Long albumId);

    Optional<Photo> findByFileNameAndAlbum_Id(String fileName, Long albumId);

}
