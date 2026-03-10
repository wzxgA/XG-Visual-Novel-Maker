package com.vnmaker.controller;

import com.vnmaker.utils.FileUtil;
import com.vnmaker.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {

    private static final String UPLOAD_DIR = "D:\\visual novel\\XG-Visual-Novel-Maker\\Assets\\Images\\";

    @PostMapping("/upload")
    public ResponseUtil<?> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Uploading file: {}", file.getOriginalFilename());
        try {
            String fileName = FileUtil.saveFile(file, UPLOAD_DIR);
            String fileUrl = "/Assets/Images/" + fileName;
            return ResponseUtil.success(fileUrl);
        } catch (IOException e) {
            log.error("File upload failed: {}", e.getMessage());
            return ResponseUtil.error("文件上传失败");
        }
    }
}
