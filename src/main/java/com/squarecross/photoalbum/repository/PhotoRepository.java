package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    int countByAlbum_Id(Long albumId);

    List<Photo> findTop4ByAlbum_IdOrderByUploadedAtDesc(Long albumId);

    @Query("SELECT p FROM Photo p WHERE LOWER(p.fileName) LIKE LOWER(concat('%', :keyword, '%')) AND p.album.id = :albumId ORDER BY p.uploadedAt DESC")
    List<Photo> findByKeywordAndAlbumIdOrderByUploadedAtDesc(@Param("keyword") String keyword, @Param("albumId") Long albumId);

    @Query("SELECT p FROM Photo p WHERE LOWER(p.fileName) LIKE LOWER(concat('%', :keyword, '%')) AND p.album.id = :albumId ORDER BY p.fileName ASC")
    List<Photo> findByKeywordAndAlbumIdOrderByFileNameAsc(@Param("keyword") String keyword, @Param("albumId") Long albumId);

    List<Photo> findByAlbum_Id(Long albumId);

    Optional<Photo> findByFileNameAndAlbum_Id(String fileName, Long albumId);

}
