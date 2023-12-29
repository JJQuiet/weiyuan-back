package com.ujn.spu.controller;

import com.ujn.spu.dao.VideoInfoRepository;
import com.ujn.spu.domain.entity.VideoInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@CrossOrigin
public class VideoController {
    //    @Value("${upload.dir}")
//    private String uploadDir;
    private static final String uploadDir = "uploads/"; // 指定上传目录
    private static final String UPLOAD_VIDEO_DIR = "uploads/video/";

    @Autowired
    private VideoInfoRepository videoInfoRepository;

    @PostMapping("/uploadVideo")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file,
                                         @RequestParam("uploader_name") String uploaderName,@RequestParam("video_description") String videoDescription) {
        // 检查目录是否存在，如果不存在则创建它
        Path uploadDirPath = Paths.get(UPLOAD_VIDEO_DIR);
        if (!Files.exists(uploadDirPath)) {
            try {
                Files.createDirectories(uploadDirPath); // 创建目录，包括所有父目录
            } catch (IOException e) {
                e.printStackTrace();// 处理目录创建失败的异常
            }
        }
        try {
            // 文件存储逻辑
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path storagePath = Paths.get(UPLOAD_VIDEO_DIR).resolve(fileName);
            Files.copy(file.getInputStream(), storagePath, StandardCopyOption.REPLACE_EXISTING);
            // 保存信息到数据库
            VideoInfoEntity videoInfo = new VideoInfoEntity();
            videoInfo.setUploaderName(uploaderName);
            videoInfo.setVideoName(fileName);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/video/")
                    .path(fileName)
                    .toUriString();
            videoInfo.setVideoPath(fileDownloadUri);
            videoInfo.setVideoDescription(videoDescription);

            videoInfoRepository.save(videoInfo);
            return ResponseEntity.ok("视频上传成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("视频上传失败");
        }
    }
    @GetMapping("/getVideos")
    public ResponseEntity<?> getVideo(@RequestParam("uploaderName") String uploaderName) {
        try {
            List<VideoInfoEntity> videoInfoList = videoInfoRepository.findByUploaderName(uploaderName);
            return ResponseEntity.ok(videoInfoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取视频失败");
        }
    }

}
