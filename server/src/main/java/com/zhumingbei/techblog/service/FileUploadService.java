package com.zhumingbei.techblog.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadAvatar(MultipartFile file, int userID);
    String uploadImage(MultipartFile file, int userID);
}
