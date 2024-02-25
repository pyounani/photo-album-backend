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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

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

    public AlbumDto changeAlbumName(Long albumId, AlbumDto albumDto) {
        Album findAlbum = albumRepository.findOne(albumId);
        if (findAlbum == null) {
            throw new NoSuchElementException("앨범 아이디가 존재하지 않습니다.");
        }

        findAlbum.setName(albumDto.getAlbumName());

        return AlbumMapper.convertToDto(findAlbum);
    }

    public void deleteAlbum(Long albumId) throws IOException{
        albumRepository.delete(albumId);
        cleanupAlbumDirectories(albumId);
    }

    private void cleanupAlbumDirectories(Long albumId) throws IOException {
        String originalPath = "Constants.PATH_PREFIX + \"/photos/original/\" + albumId";
        String thumbPath = "Constants.PATH_PREFIX + \"/photos/original/\" + albumId";
        
         deleteDirectory(originalPath);
         deleteDirectory(thumbPath);
    }

    private void deleteDirectory(String path) {
        File folder = new File(path);
        try {
            while(folder.exists()) {
                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기

                for (int j = 0; j < folder_list.length; j++) {
                    folder_list[j].delete(); //파일 삭제
                    System.out.println("파일이 삭제되었습니다.");

                }

                if(folder_list.length == 0 && folder.isDirectory()){
                    folder.delete(); //대상폴더 삭제
                    System.out.println("폴더가 삭제되었습니다.");
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void creteAlbumDirectories(Album album) throws IOException {
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/original/" + album.getId()));
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/thumb/" + album.getId()));
    }
}
