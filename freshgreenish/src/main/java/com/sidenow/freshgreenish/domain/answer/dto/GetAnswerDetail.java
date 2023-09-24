package com.sidenow.freshgreenish.domain.answer.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAnswerDetail {
    private String comment;

    @Builder
    @QueryProjection
    public GetAnswerDetail(String comment) {
        this.comment = comment;
    }
}
