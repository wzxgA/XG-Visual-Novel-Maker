package com.vnmaker.controller;

import com.vnmaker.service.UploadService;
import com.vnmaker.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadController {


    private final UploadService uploadService;

    @PostMapping("/image")
    public ResponseUtil<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = uploadService.uploadImage(file);
        return ResponseUtil.success(url);
    }

    @PostMapping("/audio")
    public ResponseUtil<String> uploadAudio(@RequestParam("file") MultipartFile file) {
        String url = uploadService.uploadAudio(file);
        return ResponseUtil.success(url);
    }
}