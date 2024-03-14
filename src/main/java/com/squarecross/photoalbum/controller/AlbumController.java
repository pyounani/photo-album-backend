package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.code.ResponseCode;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.dto.ResponseDto;
import com.squarecross.photoalbum.exception.AlbumIdNotFoundException;
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
    public ResponseEntity<ResponseDto> getAlbum(@PathVariable("albumId") final Long albumId) {
        AlbumDto res = albumService.getAlbum(albumId);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_ALBUM.getStatus().value())
                .body(new ResponseDto(ResponseCode.SUCCESS_GET_ALBUM, res));
    }

    @GetMapping("/query")
    public ResponseEntity<AlbumDto> getAlbumByQuery(@RequestParam Long albumId) {
        AlbumDto album = albumService.getAlbum(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/json_body")
    public ResponseEntity<AlbumDto> getAlbumByJson(@RequestBody AlbumDto albumDto) {
        AlbumDto album = albumService.getAlbum(albumDto.getAlbumId());
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createAlbum(@RequestBody AlbumDto albumDto) throws IOException {
        AlbumDto res = albumService.createAlbum(albumDto);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_POST_ALBUM.getStatus().value())
                .body(new ResponseDto(ResponseCode.SUCCESS_POST_ALBUM, res));
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<ResponseDto> updateAlbum(@PathVariable("albumId") Long albumId,
                                                @RequestBody AlbumDto albumDto) {
        AlbumDto res = albumService.changeAlbumName(albumId, albumDto);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_PUT_ALBUM.getStatus().value())
                .body(new ResponseDto(ResponseCode.SUCCESS_PUT_ALBUM, res));
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<ResponseDto> deleteAlbum(@PathVariable("albumId") Long albumId) throws IOException {
        albumService.deleteAlbum(albumId);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_DELETE_ALBUM.getStatus().value())
                .body(new ResponseDto(ResponseCode.SUCCESS_DELETE_ALBUM, null));
    }

}
