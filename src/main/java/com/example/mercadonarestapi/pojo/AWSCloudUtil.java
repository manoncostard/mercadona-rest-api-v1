package com.example.mercadonarestapi.pojo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.amazonaws.auth.policy.actions.S3Actions.DeleteObject;

public class AWSCloudUtil {


    private AWSCredentials awsCredentials(String accesskey, String secretkey) {
        AWSCredentials credentials = new BasicAWSCredentials(
                accesskey,
                secretkey
        );
        return credentials;
    }

    private AmazonS3 awsS3ClientBuilder(String accesskey, String secretkey) {
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials(accesskey, secretkey)))
                .withRegion(Regions.EU_NORTH_1)
                .build();
        return s3client;
    }

    public void uploadFileToS3(String fileName, byte[] fileBytes, String accesskey, String secretkey, String bucket) {
        AmazonS3 s3client = awsS3ClientBuilder(accesskey, secretkey);
        String name = "";
        String extension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            name = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex);
        }
        try {
            File file = File.createTempFile(name, extension, new File("/tmp"));

            try (OutputStream os = new FileOutputStream(file)) {
                os.write(fileBytes);
            } catch (FileNotFoundException e) {
                System.out.println("Error uploading file to S3 - FileBytes: {}" + Arrays.toString(fileBytes));

            }
            s3client.putObject(bucket, fileName, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
