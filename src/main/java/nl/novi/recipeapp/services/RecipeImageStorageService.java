
package nl.novi.recipeapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
public class RecipeImageStorageService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final String IMAGE_URL_PREFIX = "/uploads/recipes/";

    private final Path uploadDirectory;

    public RecipeImageStorageService(
            @Value("${app.upload.recipe-image-dir:uploads/recipes}")
            String uploadDirectory
    ) {
        this.uploadDirectory = Path.of(uploadDirectory)
                .toAbsolutePath()
                .normalize();
    }

    public String store(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Select an image to upload."
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "Image cannot exceed 5 MB."
            );
        }

        try {
            byte[] imageBytes = file.getBytes();

            String extension = detectImageExtension(imageBytes);

            Files.createDirectories(uploadDirectory);

            String fileName = UUID.randomUUID() + extension;

            Path destination = uploadDirectory
                    .resolve(fileName)
                    .normalize();

            if (!destination.startsWith(uploadDirectory)) {
                throw new IllegalArgumentException(
                        "Invalid file path."
                );
            }

            Files.write(
                    destination,
                    imageBytes,
                    StandardOpenOption.CREATE_NEW
            );

            return IMAGE_URL_PREFIX + fileName;

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not store the uploaded image.",
                    exception
            );
        }
    }

    public void deleteIfLocal(String imageUrl) {

        if (imageUrl == null ||
                !imageUrl.startsWith(IMAGE_URL_PREFIX)) {
            return;
        }

        String fileName = imageUrl.substring(
                IMAGE_URL_PREFIX.length()
        );

        // Alleen een bestandsnaam toestaan, geen submappen of paden.
        if (fileName.isBlank() ||
                fileName.contains("/") ||
                fileName.contains("\\")) {
            return;
        }

        Path filePath = uploadDirectory
                .resolve(fileName)
                .normalize();

        if (!filePath.startsWith(uploadDirectory)) {
            return;
        }

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not delete the image.",
                    exception
            );
        }
    }

    private String detectImageExtension(byte[] bytes) {

        if (bytes.length >= 3 &&
                (bytes[0] & 0xFF) == 0xFF &&
                (bytes[1] & 0xFF) == 0xD8 &&
                (bytes[2] & 0xFF) == 0xFF) {
            return ".jpg";
        }

        if (bytes.length >= 8 &&
                (bytes[0] & 0xFF) == 0x89 &&
                bytes[1] == 0x50 &&
                bytes[2] == 0x4E &&
                bytes[3] == 0x47 &&
                bytes[4] == 0x0D &&
                bytes[5] == 0x0A &&
                bytes[6] == 0x1A &&
                bytes[7] == 0x0A) {
            return ".png";
        }

        if (bytes.length >= 6) {
            String signature = new String(
                    bytes, 0, 6,
                    java.nio.charset.StandardCharsets.US_ASCII
            );

            if (signature.equals("GIF87a") ||
                    signature.equals("GIF89a")) {
                return ".gif";
            }
        }

        if (bytes.length >= 12 &&
                new String(bytes, 0, 4,
                        java.nio.charset.StandardCharsets.US_ASCII)
                        .equals("RIFF") &&
                new String(bytes, 8, 4,
                        java.nio.charset.StandardCharsets.US_ASCII)
                        .equals("WEBP")) {
            return ".webp";
        }

        throw new IllegalArgumentException(
                "Unsupported or invalid image format. " +
                        "Use JPEG, PNG, GIF or WebP."
        );
    }
}
