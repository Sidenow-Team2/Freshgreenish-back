package com.sidenow.freshgreenish.global.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UploadFile {
    private String originFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;

    @Builder
    public UploadFile(String originFileName, String fileName, String filePath, Long fileSize) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
