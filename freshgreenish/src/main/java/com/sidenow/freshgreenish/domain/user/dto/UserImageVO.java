package com.sidenow.freshgreenish.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserImageVO {
    private String originalFileName;
    private String storedFilePath;
    private Long file_size;

    @Builder
    public UserImageVO(String originalFileName, String storedFilePath, Long file_size) {
        this.originalFileName = originalFileName;
        this.storedFilePath = storedFilePath;
        this.file_size = file_size;
    }
}
