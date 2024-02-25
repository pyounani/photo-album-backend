package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/albums/{albumId}/photos")
public class PhotoController {

    private final PhotoService photoService;
}
