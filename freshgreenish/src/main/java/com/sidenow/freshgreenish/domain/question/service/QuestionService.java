package com.sidenow.freshgreenish.domain.question.service;

import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionDetail;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnMyPage;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnQnAPage;
import com.sidenow.freshgreenish.domain.question.dto.PostQuestion;
import com.sidenow.freshgreenish.domain.question.entity.Question;
import com.sidenow.freshgreenish.domain.question.entity.QuestionAnswerStatus;
import com.sidenow.freshgreenish.domain.user.service.UserDbService;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionDbService questionDbService;
    private final ProductDbService productDbService;
    private final UserDbService userDbService;

    @Transactional
    public void postQuestion(OAuth2User oauth, Long productId, PostQuestion post) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        productDbService.ifProductIsDeletedThrowError(productId);

        Question findQuestion = Question.builder()
                .questionTitle(post.getQuestionTitle())
                .questionContent(post.getQuestionContent())
                .isSecretMessage(post.getIsSecretMessage())
                .productId(productId)
                .userId(userId)
                .build();

        findQuestion.setStatus(QuestionAnswerStatus.WAITING_FOR_ANSWER);

        questionDbService.saveQuestion(findQuestion);
    }

    @Transactional
    public void editQuestion(Long questionId, PostQuestion edit, OAuth2User oauth) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Question findQuestion = questionDbService.ifExistsReturnQuestion(questionId);
        productDbService.ifProductIsDeletedThrowError(findQuestion.getProductId());

        if (findQuestion.getQuestionAnswerStatus().getStatus().equals(QuestionAnswerStatus.END_OF_ANSWER)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_FOR_UPDATE);
        }

        if (userId.equals(findQuestion.getUserId())) {
            throw new BusinessLogicException(ExceptionCode.INVALID_ACCESS);
        }

        findQuestion.editQuestion(edit);

        questionDbService.saveQuestion(findQuestion);
    }

    @Transactional
    public void deleteQuestion(OAuth2User oauth, Long questionId) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        Question findQuestion = questionDbService.ifExistsReturnQuestion(questionId);

        if (!findQuestion.getUserId().equals(userId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_FOR_DELETE);
        }

        if (findQuestion.getDeleted().equals(true)) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_ALREADY_DELETE);
        }

        findQuestion.setDeleted(true);

        questionDbService.saveQuestion(findQuestion);
    }

    public Page<GetQuestionOnMyPage> getQuestionOnMyPage(OAuth2User oauth, Pageable pageable) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        return questionDbService.getQuestionOnMyPage(userId, pageable);
    }

    public Page<GetQuestionOnQnAPage> getQuestionOnQnAPage(OAuth2User oauth, Pageable pageable) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        return questionDbService.getQuestionOnQnAPage(pageable);
    }

    public GetQuestionDetail getQuestionDetail(Long questionId, OAuth2User oauth) {
        Long userId = userDbService.findUserIdByOauth(oauth);
        return questionDbService.getQuestionDetail(questionId, userId);
    }
}
