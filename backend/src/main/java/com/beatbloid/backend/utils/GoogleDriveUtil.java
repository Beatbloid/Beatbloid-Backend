package com.beatbloid.backend.utils;

import com.beatbloid.backend.exceptions.UploadException;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Component
public class GoogleDriveUtil {

    private final Drive driveService;

    public GoogleDriveUtil(Drive driveService) {
        this.driveService = driveService;
    }

    public String uploadFile(MultipartFile multipartFile) {
        try {
            File fileMetadata = new File();
            fileMetadata.setName(multipartFile.getOriginalFilename());
            fileMetadata.setParents(Collections.singletonList("YOUR_GOOGLE_DRIVE_FOLDER_ID"));

            AbstractInputStreamContent content = new com.google.api.client.http.InputStreamContent(
                    multipartFile.getContentType(), multipartFile.getInputStream()
            );

            File uploadedFile = driveService.files()
                    .create(fileMetadata, content)
                    .setFields("id, webViewLink")
                    .execute();

            return uploadedFile.getWebViewLink();
        } catch (IOException e) {
            throw new UploadException("Error uploading file to Google Drive");
        }
    }
}
