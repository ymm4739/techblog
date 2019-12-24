package com.zhumingbei.techblog.service.impl;

import com.zhumingbei.techblog.config.UploadConfig;
import com.zhumingbei.techblog.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private UploadConfig uploadConfig;


    @Override
    public String uploadAvatar(MultipartFile file, int userID) {
        String parentFilepath = generateUserAvatarDir(userID);
        return upload(file, parentFilepath);
    }

    @Override
    public String uploadImage(MultipartFile file, int userID) {
        String filepath = generateImageDir(userID);
        return upload(file, filepath);
    }


    private String upload(MultipartFile file, String parentFilepath) {
        if (file.isEmpty()) {
            return "";
        }
        String root = uploadConfig.getRoot();
        String accessParentFilepath = parentFilepath.substring(root.length());

        String filename = file.getOriginalFilename();
        String newFilename = filename;
        String filepath = parentFilepath + "/" + file.getOriginalFilename();
        log.debug("filepath: {}", filepath);
        File newFile = new File(filepath);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            log.error("文件上传失败, filepath:{}, error:{}", filepath, e.getMessage());
            return null;
        }
        String url = uploadConfig.getUrlPrefix()  + accessParentFilepath + "/" + newFilename;
        return url;
    }


    private String generateUserAvatarDir(int userID) {
        String dir = uploadConfig.getAvatarRootPath() + "/" + userID;
        mkdir(dir);
        return dir;
    }

    private String generateImageDir(int userID) {
        String dir = uploadConfig.getImageRootPath() + "/" + userID;
        mkdir(dir);
        return dir;
    }

    private void mkdir(String filepath) {
        File dir = new File(filepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }
}
