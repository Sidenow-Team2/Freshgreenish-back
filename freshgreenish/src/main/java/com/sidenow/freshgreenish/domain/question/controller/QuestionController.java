package com.sidenow.freshgreenish.domain.question.controller;

import com.sidenow.freshgreenish.domain.dto.MultiResponseDto;
import com.sidenow.freshgreenish.domain.dto.SingleResponseDto;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionDetail;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnMyPage;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnQnAPage;
import com.sidenow.freshgreenish.domain.question.dto.PostQuestion;
import com.sidenow.freshgreenish.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/product/{productId}")
    public ResponseEntity postQuestion(@PathVariable("productId") Long productId,
                                       @RequestBody @Valid PostQuestion post,
                                       @AuthenticationPrincipal OAuth2User oauth) {
        questionService.postQuestion(oauth, productId, post);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity editQuestion(@PathVariable("questionId") Long questionId,
                                       @RequestBody @Valid PostQuestion edit,
                                       @AuthenticationPrincipal OAuth2User oauth) {
        questionService.editQuestion(questionId, edit, oauth);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity getQuestionInMyPage(Pageable pageable,
                                              @AuthenticationPrincipal OAuth2User oauth) {
        Page<GetQuestionOnMyPage> questionList = questionService.getQuestionOnMyPage(oauth, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(questionList));
    }

    @GetMapping("/page")
    public ResponseEntity getQuestionInQnAPage(Pageable pageable,
                                               @AuthenticationPrincipal OAuth2User oauth) {
        Page<GetQuestionOnQnAPage> questionList = questionService.getQuestionOnQnAPage(oauth, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(questionList));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity getQuestionDetail(@PathVariable("questionId") Long questionId,
                                            @AuthenticationPrincipal OAuth2User oauth) {
        GetQuestionDetail question = questionService.getQuestionDetail(questionId, oauth);
        return ResponseEntity.ok().body(new SingleResponseDto<>(question));
    }

    @PatchMapping("/{questionId}/deleted")
    public ResponseEntity deleteQuestion(@PathVariable("questionId") Long questionId,
                                         @AuthenticationPrincipal OAuth2User oauth) {
        questionService.deleteQuestion(oauth, questionId);
        return ResponseEntity.ok().build();
    }
}
