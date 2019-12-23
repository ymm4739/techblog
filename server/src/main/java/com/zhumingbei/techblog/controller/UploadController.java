package com.zhumingbei.techblog.controller;

import com.zhumingbei.techblog.common.ApiResponse;
import com.zhumingbei.techblog.common.CustomUserPrincipal;
import com.zhumingbei.techblog.config.UploadConfig;
import com.zhumingbei.techblog.util.UploadedFileManagerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private UploadedFileManagerUtil fileManagerUtil;

    @PostMapping("/upload/avatar/add")
    public ApiResponse avatarUpload(MultipartFile file) {
        int userID = CustomUserPrincipal.getUserID();
        String parentFilepath = uploadConfig.getAvatar() + File.pathSeparator + userID;
        String result = fileManagerUtil.upload(file, parentFilepath);
        if (result == null) {
            return ApiResponse.of(50004, "上传失败");
        }else if (result.isEmpty()){
            return ApiResponse.of(40000, "文件内容为空");
        }
        return ApiResponse.ofSuccess("文件上传成功", result);

    }

    @PostMapping("/upload/image")
    public ApiResponse imageUpload(MultipartFile file) {
        if (file == null) {
            return ApiResponse.of(40000, "上传失败，文件未找到");
        }
        int userID = CustomUserPrincipal.getUserID();
        String parentFilepath = uploadConfig.getImage() + "/" + userID;
        String result = fileManagerUtil.uploadImage(file, userID);
        if (result == null) {
            return ApiResponse.of(50004, "上传失败");
        }else if (result.isEmpty()){
            return ApiResponse.of(40000, "文件内容为空");
        }
        return ApiResponse.ofSuccess("文件上传成功", result);

    }

}
