package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public Album getAlbum(Long albumId) {
        Album findAlbum = albumRepository.findOne(albumId);
        if(findAlbum == null) {
            throw new IllegalStateException("존재하지 않는 앨범 아이디입니다.");
        }
        return findAlbum;
    }
}
