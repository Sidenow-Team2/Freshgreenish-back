package com.sidenow.freshgreenish.domain.answer.service;

import com.sidenow.freshgreenish.domain.answer.dto.PostAnswer;
import com.sidenow.freshgreenish.domain.answer.entity.Answer;
import com.sidenow.freshgreenish.domain.question.entity.Question;
import com.sidenow.freshgreenish.domain.question.entity.QuestionAnswerStatus;
import com.sidenow.freshgreenish.domain.question.service.QuestionDbService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerDbService answerDbService;
    private final QuestionDbService questionDbService;

    @Transactional
    public void postAnswer(Long questionId, PostAnswer post) {
        Question findQuestion = questionDbService.ifExistsReturnQuestion(questionId);
        questionDbService.ifQuestionIsDeletedThrowError(questionId);

        Answer findAnswer = Answer.builder()
                .questionId(findQuestion.getQuestionId())
                .comment(post.getComment())
                .build();

        findQuestion.setStatus(QuestionAnswerStatus.END_OF_ANSWER);

        answerDbService.saveAnswer(findAnswer);
    }

    @Transactional
    public void editAnswer(Long answerId, PostAnswer edit) {
        Answer findAnswer = answerDbService.ifExistsReturnAnswer(answerId);
        questionDbService.ifQuestionIsDeletedThrowError(findAnswer.getQuestionId());
        findAnswer.editAnswer(edit);

        answerDbService.saveAnswer(findAnswer);
    }

    @Transactional
    public void deleteAnswer(Long answerId) {
        Answer findAnswer = answerDbService.ifExistsReturnAnswer(answerId);
        findAnswer.setDeleted(true);

        answerDbService.saveAnswer(findAnswer);
    }
}
