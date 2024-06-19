package com.sidenow.freshgreenish.domain.user.service;

import com.sidenow.freshgreenish.domain.user.dto.UserImageVO;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserImageFileHandler {
    public UserImageVO parseImageURL(
            MultipartFile multipartFile
    ) throws Exception {

        UserImageVO imageVO = new UserImageVO();
        if (multipartFile.isEmpty()) {
            return imageVO;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());
        String absolutePath = new File("").getAbsolutePath() + "\\";


        String path = "C:/projects/freshgreenish-back/freshgreenish/src/main/resources/img/" + current_date;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }


        if (!multipartFile.isEmpty()) {
            String contentType = multipartFile.getContentType();
            String originalFileExtension;

            if (ObjectUtils.isEmpty(contentType)) {
                throw new BusinessLogicException(ExceptionCode.INVALID_FILE_EXTENSION);
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if (contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                }
                else {
                    throw new BusinessLogicException(ExceptionCode.INVALID_FILE_EXTENSION);
                }
            }

            String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
            imageVO = UserImageVO.builder()
                    .originalFileName(multipartFile.getOriginalFilename())
                    .storedFilePath("/img/" + current_date +"/"+ new_file_name)
                    .file_size(multipartFile.getSize())
                    .build();

            file = new File(path + "/" + new_file_name);
            multipartFile.transferTo(file);
        }

        return imageVO;
    }
}