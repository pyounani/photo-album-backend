package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.Constants;
import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;
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
            throw new EntityNotFoundException("존재하지 않는 앨범 아이디입니다.");
        }
        AlbumDto albumDto = AlbumMapper.convertToDto(findAlbum.get());
        albumDto.setCount(photoRepository.countAlbum(albumId));
        return albumDto;

    }

    public AlbumDto changeAlbumName(Long albumId, AlbumDto albumDto) {
        Optional<Album> findAlbum = albumRepository.findOne(albumId);
        if (findAlbum == null) {
            throw new NoSuchElementException("앨범 아이디가 존재하지 않습니다.");
        }

        findAlbum.get().setName(albumDto.getAlbumName());

        return AlbumMapper.convertToDto(findAlbum.get());
    }

    public void deleteAlbum(Long albumId) throws IOException{
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
                File[] folder_list = path.toFile().listFiles(); // Get the list of files

                if (folder_list != null) {
                    for (int j = 0; j < folder_list.length; j++) {
                        if (folder_list[j].isDirectory()) {
                            deleteDirectory(folder_list[j].toPath()); // Recursively delete subdirectories
                        } else {
                            folder_list[j].delete(); // Delete files
                            System.out.println("File deleted: " + folder_list[j].getName());
                        }
                    }
                }

                Files.deleteIfExists(path); // Delete the directory itself
                System.out.println("Folder deleted: " + path.toString());
            } else {
                System.out.println("Directory does not exist: " + path.toString());
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
