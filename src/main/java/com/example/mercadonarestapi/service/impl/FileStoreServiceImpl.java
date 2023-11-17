package com.example.mercadonarestapi.service.impl;



import com.example.mercadonarestapi.pojo.AWSCloudUtil;
import com.example.mercadonarestapi.service.FileStoreService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStoreServiceImpl implements FileStoreService {

    private static final String ACCESS_KEY_ID = System.getenv("ACCESS_KEY_ID");
    private static final String SECRET_ACCESS_KEY = System.getenv("SECRET_ACCESS_KEY");
    private static final String AWS_BUCKET = System.getenv("AWS_BUCKET");

    public Boolean uploadMultipartFileS3(MultipartFile data, String fileName) {
        try {
            AWSCloudUtil util = new AWSCloudUtil();
            util.uploadFileToS3(fileName , data.getBytes(), ACCESS_KEY_ID, SECRET_ACCESS_KEY, AWS_BUCKET);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
