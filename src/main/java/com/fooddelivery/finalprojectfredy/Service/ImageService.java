package com.fooddelivery.finalprojectfredy.Service;

import com.fooddelivery.finalprojectfredy.utils.Image;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ImageService {
    public static String projectDirectory = System.getProperty("user.dir");
    public static String userImageDir = projectDirectory + "/src/main/resources/static/img/UserImage/";//"/src/main/resources/static/img/UserImage/"
    private static List<Image> imageList = Arrays.asList(
            new Image("Title 1", "Description 1", "/img/offer/promo1.png"),
            new Image("Title 2", "Description 2", "/img/offer/promo2.png"),
            new Image("Title 3", "Description 3", "/img/offer/promo3.png"),
            new Image("Title 4", "Description 4", "/img/offer/promo4.png"),
            new Image("Title 5", "Description 5", "/img/offer/promo5.png")
    );

    public static List<Image> getImageList() {
        return imageList;
    }

    public static void addImage(Image image) {
        imageList.add(image);
    }

    public static String saveImage(MultipartFile image) throws IOException {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String uniqueFileName = generateUniqueFileName(originalFileName);

        String directory = userImageDir;
        Path directoryPath = Paths.get(directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        Path imagePath = Path.of(directory, uniqueFileName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private static String generateUniqueFileName(String originalFileName) {
        String extension = StringUtils.getFilenameExtension(originalFileName);
        String baseName = StringUtils.stripFilenameExtension(originalFileName);
        String uniqueId = UUID.randomUUID().toString();
        return baseName + "_" + uniqueId + "." + extension;
    }
}
