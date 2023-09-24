package com.sidenow.freshgreenish.domain.question.service;

import com.sidenow.freshgreenish.domain.question.dto.GetQuestionDetail;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnMyPage;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnQnAPage;
import com.sidenow.freshgreenish.domain.question.entity.Question;
import com.sidenow.freshgreenish.domain.question.repository.QuestionRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionDbService {
    private final QuestionRepository questionRepository;

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public Question ifExistsReturnQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    public void ifQuestionIsDeletedThrowError(Long questionId) {
        Boolean isDeleted = questionRepository.isDeleted(questionId);
        if (isDeleted.equals(true)) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND);
        }
    }

    public Page<GetQuestionOnMyPage> getQuestionInMyPage(Long userId, Pageable pageable) {
        return questionRepository.getQuestionOnMyPage(userId, pageable);
    }

    public Page<GetQuestionOnQnAPage> getQuestionInQnAPage(Pageable pageable) {
        return questionRepository.getQuestionOnQnAPage(pageable);
    }

    public GetQuestionDetail getQuestionDetail(Long questionId, Long userId) {
        return questionRepository.getQuestionDetail(questionId, userId);
    }
}
