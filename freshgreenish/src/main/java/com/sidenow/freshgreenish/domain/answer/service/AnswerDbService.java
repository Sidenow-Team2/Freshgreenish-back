package com.sidenow.freshgreenish.domain.answer.service;

import com.sidenow.freshgreenish.domain.answer.dto.GetAnswerDetail;
import com.sidenow.freshgreenish.domain.answer.entity.Answer;
import com.sidenow.freshgreenish.domain.answer.repository.AnswerRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerDbService {
    private final AnswerRepository answerRepository;

    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    public Answer ifExistsReturnAnswer(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
    }

    public GetAnswerDetail getAnswerDetail(Long questionId) {
        return answerRepository.getAnswerDetail(questionId);
    }
}
