package com.sidenow.freshgreenish.domain.question.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sidenow.freshgreenish.domain.question.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetQuestionOnMyPage {
    private Long questionId;
    private Long productId;
    private String questionTitle;
    private String questionContent;
    private LocalDateTime lastModifiedAt;
    private String title;
    private String productFirstImage;
    private Boolean isSecretMessage;
    private String questionAnswerStatus;
    private String answerComment;

    @Builder
    @QueryProjection
    public GetQuestionOnMyPage(Question question, String title, String productFirstImage, String answerComment) {
        this.questionId = question.getQuestionId();
        this.productId = question.getProductId();
        this.questionTitle = question.getQuestionTitle();
        this.questionContent = question.getQuestionContent();
        this.lastModifiedAt = question.getLastModifiedAt();
        this.isSecretMessage = question.getIsSecretMessage();
        this.questionAnswerStatus = question.getQuestionAnswerStatus().getStatus();
        this.title = title;
        this.productFirstImage = productFirstImage;
        this.answerComment = answerComment;
    }
}
