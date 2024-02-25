package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.Constants;
import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;

    public AlbumDto createAlbum(AlbumDto albumDto) throws IOException {
        Album album = AlbumMapper.convertToModel(albumDto);
        albumRepository.save(album);
        creteAlbumDirectories(album);
        return AlbumMapper.convertToDto(album);
    }

    public AlbumDto getAlbum(Long albumId) {
        Album findAlbum = albumRepository.findOne(albumId);
        if(findAlbum == null) {
            throw new IllegalStateException("존재하지 않는 앨범 아이디입니다.");
        } else {
            AlbumDto albumDto = AlbumMapper.convertToDto(findAlbum);
            System.out.println(photoRepository.countAlbum(albumId));
            albumDto.setCount(photoRepository.countAlbum(albumId));
            return albumDto;
        }
    }
    private void creteAlbumDirectories(Album album) throws IOException {
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/original/" + album.getId()));
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + album.getId()));
    }
}
