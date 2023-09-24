package com.sidenow.freshgreenish.global.file;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileHandler {
    public List<UploadFile> uploadFileList(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> fileList = new ArrayList<>();

        if (multipartFiles.isEmpty()) {
            return fileList;
        }

        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
        String path = "images";
        File file = new File(path);

        if (!file.exists()) {
            file.mkdir();
        }

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFileExtension = "";
            String contentType = multipartFile.getContentType();

            if (ObjectUtils.isEmpty(contentType)) {
                break;
            } else {
                if (contentType.contains("image/jpeg"))
                    originalFileExtension = ".jpg";
                else if (contentType.contains("image/png"))
                    originalFileExtension = ".png";
                else if (contentType.contains("image/gif"))
                    originalFileExtension = ".gif";
                else
                    break;
            }

            String fileName = createFileName(multipartFile);
            UploadFile uploadFile = createUploadFile(multipartFile, fileName, path);

            fileList.add(uploadFile);

            file = new File(absolutePath + path + File.separator + fileName);
            multipartFile.transferTo(file);

            file.setWritable(true);
            file.setReadable(true);
        }

        return fileList;
    }

    public UploadFile uploadFile(MultipartFile multipartFile) throws IOException {
        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
        String path = "images";
        File file = new File(path);

        if (!file.exists()) {
            file.mkdir();
        }

        String fileName = createFileName(multipartFile);
        UploadFile uploadFile = createUploadFile(multipartFile, fileName, path);

        file = new File(absolutePath + path + File.separator + fileName);
        multipartFile.transferTo(file);

        file.setWritable(true);
        file.setReadable(true);

        return uploadFile;
    }

    public void delete(String absolutePath) {
        File file = new File(absolutePath);
        file.delete();
    }

    private String createFileName(MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return uuid + extension;
    }

    private UploadFile createUploadFile(MultipartFile multipartFile, String fileName, String path) {
        return UploadFile.builder()
                .originFileName(multipartFile.getOriginalFilename())
                .fileName(fileName)
                .filePath(path + File.separator + fileName)
                .fileSize(multipartFile.getSize())
                .build();
    }
}
