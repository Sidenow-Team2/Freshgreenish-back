package com.sidenow.freshgreenish.domain.answer.repository;

import com.sidenow.freshgreenish.domain.answer.dto.GetAnswerDetail;

public interface CustomAnswerRepository {
    GetAnswerDetail getAnswerDetail(Long questionId);
}
