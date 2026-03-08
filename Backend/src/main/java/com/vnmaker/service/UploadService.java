package com.vnmaker.service;

import com.vnmaker.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadService {

    @Value("${vn.resource.path}")
    private String resourcePath;

    public String uploadImage(MultipartFile file) {
        try {
            return FileUtil.saveFile(file, resourcePath + "images/");
        } catch (IOException e) {
            throw new RuntimeException("图片上传失败", e);
        }
    }

    public String uploadAudio(MultipartFile file) {
        try {
            return FileUtil.saveFile(file, resourcePath + "audio/");
        } catch (IOException e) {
            throw new RuntimeException("音频上传失败", e);
        }
    }
}