package com.example.app.helpers;

import com.example.app.responses.ResponseCode;
import com.example.app.models.PMAttachment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileUploadHelper {

    private static final String STORAGE_DIRECTORY = "storage/public";

    /**
     * Upload a file and return its path.
     *
     * @param file      The file to upload
     * @param directory The directory to upload the file to
     * @return The path of the uploaded file
     * @throws IOException if file upload fails
     */
    public static String upload(MultipartFile file, String directory) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path directoryPath = Paths.get(STORAGE_DIRECTORY, directory);
        Files.createDirectories(directoryPath); // Create directories if not exists

        Path filePath = directoryPath.resolve(fileName);
        file.transferTo(filePath.toFile());

        return filePath.toString();
    }

    /**
     * Upload a file with tenant ID and category and return file information.
     *
     * @param file      The file to upload
     * @param tenantId  The tenant ID for directory organization
     * @param directory The base directory for the file
     * @param category  The category of the file
     * @return A map of file details
     */
    public static ResponseEntity<?> uploadFile(MultipartFile file, String tenantId, String directory, String category) {
        try {
            // File details
            String fileName = file.getOriginalFilename();
            long fileSizeKB = file.getSize() / 1024;
            String fileType = getExtension(fileName);

            // Validate the file using PMAttachment model
            PMAttachment pmAttachment = new PMAttachment(fileName, fileSizeKB, fileType, category);

            // Organization-specific directory
            String organizationDirectory = directory + "/" + tenantId;
            String filePath = upload(file, organizationDirectory);

            if (!new File(filePath).exists()) {
                throw new IOException("Failed to save the file in the specified location.");
            }

            // Return file information
            Map<String, Object> response = new HashMap<>();
            response.put("file_path", filePath);
            response.put("file_name", pmAttachment.getName());
            response.put("file_size_kb", fileSizeKB);
            response.put("file
