package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.service.AlbumService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumDto> getAlbum(@PathVariable("albumId") final Long albumId) {
        AlbumDto album = albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity<AlbumDto> getAlbumByQuery(@RequestParam Long albumId) {
        AlbumDto album = albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/json_body")
    public ResponseEntity<AlbumDto> getAlbumByJson(@RequestBody AlbumResponseDto albumResponseDto) {
        AlbumDto album = albumService.getAlbum(albumResponseDto.getAlbumId());
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody AlbumDto albumDto) throws IOException {
        AlbumDto album = albumService.createAlbum(albumDto);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable("albumId") Long albumId,
                                                @RequestBody AlbumDto albumDto) {
        AlbumDto res = albumService.changeAlbumName(albumId, albumDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable("albumId") Long albumId) throws IOException {
        albumService.deleteAlbum(albumId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Getter

    public static class AlbumResponseDto {
        private Long albumId;
    }

}
