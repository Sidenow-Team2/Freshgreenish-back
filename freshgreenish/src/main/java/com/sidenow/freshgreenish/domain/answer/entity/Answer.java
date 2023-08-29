package com.sidenow.freshgreenish.domain.answer.entity;

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
@SQLDelete(sql = "UPDATE answer SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Answer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_ID")
    private Long answerId;

    @Column(length = 2000)
    private String comment;

    private Long questionId;

    @Builder
    public Answer(Long answerId, String comment, Long questionId) {
        this.answerId = answerId;
        this.comment = comment;
        this.questionId = questionId;
    }

}
