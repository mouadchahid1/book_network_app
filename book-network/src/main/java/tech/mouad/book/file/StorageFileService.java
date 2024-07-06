package tech.mouad.book.file;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageFileService {
    @Value("${application.file.path.photo-path}")
    private String uploadFilePath;

    public String saveFile(
            @NonNull MultipartFile file,
            @NonNull Integer userId) {
        final String fileUploadSubPath = "users" + File.separator + userId;
        return uploadFile(file, fileUploadSubPath);
    }

    private String uploadFile(
            @NonNull MultipartFile sourceFile,
            @NonNull String fileUploadSubPath) {
        String finalUploadFile = uploadFilePath + File.separator + fileUploadSubPath;
        File targetFile = new File(finalUploadFile);
        if (!targetFile.exists()) {
            boolean createdPath = targetFile.mkdirs();
            if (!createdPath) {
                log.warn("Error to create path" + targetFile);
                return null;
            }
        }
        String fileExtension = getExtension(sourceFile.getOriginalFilename());
        String finalTargetFile = finalUploadFile + File.separator + UUID.randomUUID().toString() + "." + fileExtension;
        Path targetFilePath = Paths.get(finalTargetFile);
        try {
            Files.write(targetFilePath, sourceFile.getBytes());
            log.info("The file add success ful");
            return finalTargetFile;
        } catch (IOException e) {
            log.error("Error to save the file" + e);
        }
        return null;

    }

    private String getExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1).toLowerCase();
    }
}
