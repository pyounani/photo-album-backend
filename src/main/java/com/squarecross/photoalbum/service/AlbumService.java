package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.Constants;
import com.squarecross.photoalbum.code.ErrorCode;
import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.exception.AlbumIdNotFoundException;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public AlbumDto getAlbum(Long albumId) {
        Optional<Album> findAlbum = albumRepository.findOne(albumId);
        if(findAlbum.isEmpty()) {
            throw new AlbumIdNotFoundException(ErrorCode.ALBUMID_NOT_FOUND);
        }
        AlbumDto albumDto = AlbumMapper.convertToDto(findAlbum.get());
        albumDto.setCount(photoRepository.countAlbum(albumId));
        return albumDto;
    }

    public AlbumDto changeAlbumName(Long albumId, AlbumDto albumDto) {
        Optional<Album> findAlbum = albumRepository.findOne(albumId);
        if (findAlbum.isEmpty()) {
            throw new AlbumIdNotFoundException(ErrorCode.ALBUMID_NOT_FOUND);
        }
        findAlbum.get().setName(albumDto.getAlbumName());
        return AlbumMapper.convertToDto(findAlbum.get());
    }

    public void deleteAlbum(Long albumId) throws IOException{
        Optional<Album> findAlbum = albumRepository.findOne(albumId);
        if (findAlbum.isEmpty()) {
            throw new AlbumIdNotFoundException(ErrorCode.ALBUMID_NOT_FOUND);
        }
        cleanupAlbumDirectories(albumId);
        albumRepository.delete(albumId);
    }

    private void cleanupAlbumDirectories(Long albumId) throws IOException {
        Path originalPath = Paths.get(Constants.PATH_PREFIX + "/photos/original/" + albumId);
        Path thumbPath = Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + albumId);

        deleteDirectory(originalPath);
        deleteDirectory(thumbPath);
    }

    private void deleteDirectory(Path path) {
        try {
            if (Files.exists(path)) {
                File[] folder_list = path.toFile().listFiles(); // 앨범 내의 사진들을 가져옵니다.

                if (folder_list != null) {
                    for (int j = 0; j < folder_list.length; j++) {
                        if (folder_list[j].isDirectory()) {
                            deleteDirectory(folder_list[j].toPath()); // 하위 디렉터리를 재귀적으로 삭제합니다.
                        } else {
                            folder_list[j].delete(); // 파일을 삭제합니다.
                        }
                    }
                }

                Files.deleteIfExists(path); // 앨범을 삭제합니다.
            } else {
                System.out.println("잘못된 경로입니다: " + path.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void creteAlbumDirectories(Album album) throws IOException {
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/original/" + album.getId()));
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + album.getId()));
    }
}
