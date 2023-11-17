package com.example.mercadonarestapi.pojo;

import java.util.HashMap;
import java.util.Map;

public class PictureUtil {

    public String buildFileNameWithCorrectExt(String fileName, String mimeType) {
        String name;
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            name = fileName.substring(0, dotIndex);
        } else {
            name = fileName;
        }
        Map<String, String> map = new HashMap<>();
        map.put("image/png", ".png");
        map.put("image/jpeg", ".jpg");
        String extension = map.getOrDefault(mimeType, ".unknown");
        return name + extension;
    }
}
