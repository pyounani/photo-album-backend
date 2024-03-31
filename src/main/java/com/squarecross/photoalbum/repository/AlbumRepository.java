package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByNameContainingOrderByNameAsc(String keyword);

    List<Album> findByNameContainingOrderByCreatedAtDesc(String keyword);
}
