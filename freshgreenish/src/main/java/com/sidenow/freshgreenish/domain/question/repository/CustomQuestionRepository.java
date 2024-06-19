package com.sidenow.freshgreenish.domain.question.repository;

import com.sidenow.freshgreenish.domain.question.dto.GetQuestionDetail;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnQnAPage;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnMyPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomQuestionRepository {
    Page<GetQuestionOnMyPage> getQuestionOnMyPage(Long userId, Pageable pageable);
    Page<GetQuestionOnQnAPage> getQuestionOnQnAPage(Pageable pageable);
    GetQuestionDetail getQuestionDetail(Long questionId, Long userId);

    Boolean isDeleted(Long questionId);
}
