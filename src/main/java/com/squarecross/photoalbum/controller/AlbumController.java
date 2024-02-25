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

    @Getter
    public static class AlbumResponseDto {
        private Long albumId;
    }

}
