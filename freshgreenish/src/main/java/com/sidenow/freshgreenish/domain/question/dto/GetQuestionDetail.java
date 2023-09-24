package com.sidenow.freshgreenish.domain.question.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sidenow.freshgreenish.domain.question.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetQuestionDetail {
    private Long questionId;
    private Long productId;
    private String questionTitle;
    private String questionContent;
    private LocalDateTime lastModifiedAt;
    private Boolean isSecretMessage;
    private String title;
    private String productFirstImage;

    @Builder
    @QueryProjection
    public GetQuestionDetail(Question question, String title, String productFirstImage) {
        this.questionId = question.getQuestionId();
        this.productId = question.getProductId();
        this.questionTitle = question.getQuestionTitle();
        this.questionContent = question.getQuestionContent();
        this.lastModifiedAt = question.getLastModifiedAt();
        this.isSecretMessage = question.getIsSecretMessage();
        this.title = title;
        this.productFirstImage = productFirstImage;
    }
}
