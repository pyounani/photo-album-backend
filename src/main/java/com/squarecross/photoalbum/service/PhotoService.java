package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.Constants;
import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.mapper.PhotoMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final AlbumRepository albumRepository;

    private final String original_path = Constants.PATH_PREFIX + "/photos/original";
    private final String thumb_path = Constants.PATH_PREFIX + "/photos/thumb";

    public PhotoDto getPhoto(Long photoId) {
        Photo findPhoto = photoRepository.findOne(photoId);
        return PhotoMapper.convertToDto(findPhoto);
    }

    public PhotoDto savePhoto(MultipartFile file, Long albumId) {
        Album findAlbum = albumRepository.findOne(albumId);
        if (findAlbum == null) {
            throw new EntityNotFoundException();
        }

        String fileName = file.getOriginalFilename();
        int fileSize = (int) file.getSize();

        fileName = getNextFileName(fileName, albumId);

        Photo photo = new Photo();
        photo.setFileName(fileName);

        photoRepository.save(photo);
        return PhotoMapper.convertToDto(photo);
    }

    private String getNextFileName(String fileName, Long albumId) {
        String fileNameNoExt = StringUtils.stripFilenameExtension(fileName);
        String ext = StringUtils.getFilenameExtension(fileName);

        Optional<Photo> res = photoRepository.findByFileNameAndAlbum_AlbumId(fileName, albumId);

        int count = 2;
        while (res.isPresent()) {
            fileName = String.format("%s (%d).%s", fileNameNoExt, count, ext);
            res = photoRepository.findByFileNameAndAlbum_AlbumId(fileName, albumId);
            count++;
        }
        return fileName;
    }

    private void saveFile(MultipartFile file, Long AlbumId, String fileName) throws IOException {
        String filePath = AlbumId + "/" + fileName;
        Files.copy(file.getInputStream(), Paths.get(original_path + "/" + filePath));


    }


}
