package com.example.mercadonarestapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoreService {
    Boolean uploadMultipartFileS3(MultipartFile data, String name);


}
