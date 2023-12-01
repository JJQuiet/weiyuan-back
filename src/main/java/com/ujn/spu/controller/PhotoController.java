package com.ujn.spu.controller;

import com.ujn.spu.dao.PhotoInfoRepository;
import com.ujn.spu.domain.entity.PhotoInfoEntity;
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
public class PhotoController {
    //    @Value("${upload.dir}")
//    private String uploadDir;
    private static final String uploadDir = "uploads/"; // 指定上传目录
    private static final String UPLOAD_PHOTO_DIR = "uploads/photo/";

    @Autowired
    private PhotoInfoRepository photoInfoRepository;

    @PostMapping("/uploadPhoto")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file,
                                         @RequestParam("uploader_name") String uploaderName) {
        try {
            // 文件存储逻辑
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path storagePath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), storagePath, StandardCopyOption.REPLACE_EXISTING);

            // 保存信息到数据库
            PhotoInfoEntity photoInfo = new PhotoInfoEntity();
            photoInfo.setUploaderName(uploaderName);
            photoInfo.setPhotoName(fileName);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();
            photoInfo.setPhotoPath(fileDownloadUri);
            photoInfoRepository.save(photoInfo);

            return ResponseEntity.ok("文件上传成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败");
        }
    }

    @GetMapping("/getPhotos")
    public ResponseEntity<?> getPhoto(@RequestParam("uploaderName") String uploaderName) {
        try {
            List<PhotoInfoEntity> photoInfoList = photoInfoRepository.findByUploaderName(uploaderName);
            return ResponseEntity.ok(photoInfoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取照片失败");
        }
    }

}
