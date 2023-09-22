package com.sidenow.freshgreenish.domain.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostQuestion {
    private String questionTitle;
    private String questionContent;
    private Boolean isSecretMessage;
}
