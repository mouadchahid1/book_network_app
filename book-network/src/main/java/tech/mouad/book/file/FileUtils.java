package tech.mouad.book.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {

    public static byte[] readFileFromLocation(String fileLocation) {
        if (StringUtils.isBlank(fileLocation)) {
            return null;
        }
        try {
            Path filePath = new File(fileLocation).toPath();
            return Files.readAllBytes(filePath);
        } catch (IOException exp) {
            log.error("Nous arivons pas a lire le fichier");
        }
        return null ;
    }
}
