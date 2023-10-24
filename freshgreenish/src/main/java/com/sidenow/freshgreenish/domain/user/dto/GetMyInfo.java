package com.sidenow.freshgreenish.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetMyInfo {

    private String filePath;
    private String nickname;
    private Integer saved_money;

}
