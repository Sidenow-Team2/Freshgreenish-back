package com.sidenow.freshgreenish.domain.question.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sidenow.freshgreenish.domain.question.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetQuestionOnQnAPage {
    private Long questionId;
    private Long productId;
    private String questionTitle;
    private String questionContent;
    private LocalDateTime lastModifiedAt;
    private Boolean isSecretMessage;
    private String questionAnswerStatus;
    private String nickname;
    private Long userId;
    private String answerComment;

    @Builder
    @QueryProjection
    public GetQuestionOnQnAPage(Question question, String nickname, Long userId, String answerComment) {
        this.questionId = question.getQuestionId();
        this.productId = question.getProductId();
        this.questionTitle = question.getQuestionTitle();
        this.questionContent = question.getQuestionContent();
        this.lastModifiedAt = question.getLastModifiedAt();
        this.isSecretMessage = question.getIsSecretMessage();
        this.questionAnswerStatus = question.getQuestionAnswerStatus().getStatus();
        this.nickname = nickname;
        this.userId = userId;
        this.answerComment = answerComment;
    }
}
