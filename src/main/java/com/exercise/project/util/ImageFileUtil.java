package com.exercise.project.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileUtil {

    public static String getExtensionFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            int extIndex = fileName.lastIndexOf(".");
            return fileName.substring(extIndex);
        }
        return null;
    }

    public static String storeImage(MultipartFile imageFile, String uploadDir) {
        if (imageFile != null) {
            try {
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + getExtensionFile(imageFile);
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath))
                    Files.createDirectories(uploadPath);
                Files.copy(
                    imageFile.getInputStream(),
                    Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING
                );

                return storageFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String getImageUrl(String imagePath, String uploadDir) {
        return (imagePath != null)
            ? (uploadDir + imagePath).replace("public/", "")
            : null;
    }

}
