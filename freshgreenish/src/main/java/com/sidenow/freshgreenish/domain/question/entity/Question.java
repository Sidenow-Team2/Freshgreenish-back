package com.sidenow.freshgreenish.domain.question.entity;

import com.sidenow.freshgreenish.domain.question.dto.PostQuestion;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Long questionId;

    @Column(length = 2000)
    private String questionTitle;

    @Column(length = 2000)
    private String questionContent;

    private Boolean isSecretMessage;

    @Enumerated(value = EnumType.STRING)
    private QuestionAnswerStatus questionAnswerStatus;

    private Long productId;
    private Long answerId;
    private Long userId;

    @Builder
    public Question(Long questionId, String questionTitle, String questionContent,
                    Boolean isSecretMessage, Long productId, Long answerId, Long userId) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
        this.isSecretMessage = isSecretMessage;
        this.productId = productId;
        this.answerId = answerId;
        this.userId = userId;
    }

    public void setStatus(QuestionAnswerStatus status) {
        this.questionAnswerStatus = status;
    }

    public void editQuestion(PostQuestion edit) {
        this.questionTitle = edit.getQuestionTitle();
        this.questionContent = edit.getQuestionContent();
        this.isSecretMessage = edit.getIsSecretMessage();
    }
}
