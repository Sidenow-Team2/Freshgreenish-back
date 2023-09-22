package com.sidenow.freshgreenish.domain.question.controller;

import com.sidenow.freshgreenish.domain.dto.MultiResponseDto;
import com.sidenow.freshgreenish.domain.dto.SingleResponseDto;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionDetail;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnMyPage;
import com.sidenow.freshgreenish.domain.question.dto.GetQuestionOnQnAPage;
import com.sidenow.freshgreenish.domain.question.dto.PostQuestion;
import com.sidenow.freshgreenish.domain.question.service.QuestionDbService;
import com.sidenow.freshgreenish.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionDbService questionDbService;

    @PostMapping("/product/{productId}")
    public ResponseEntity postQuestion(@PathVariable("productId") Long productId,
                                       @RequestBody @Valid PostQuestion post) {
        Long userId = 1L;
        questionService.postQuestion(userId, productId, post);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity editQuestion(@PathVariable("questionId") Long questionId,
                                       @RequestBody @Valid PostQuestion edit) {
        Long userId = 1L;
        questionService.editQuestion(questionId, edit);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity getQuestionInMyPage(Pageable pageable) {
        Long userId = 1L;
        Page<GetQuestionOnMyPage> questionList = questionDbService.getQuestionInMyPage(userId, pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(questionList));
    }

    @GetMapping("/page")
    public ResponseEntity getQuestionInQnAPage(Pageable pageable) {
        Page<GetQuestionOnQnAPage> questionList = questionDbService.getQuestionInQnAPage(pageable);
        return ResponseEntity.ok().body(new MultiResponseDto<>(questionList));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity getQuestionDetail(@PathVariable("questionId") Long questionId) {
        Long userId = 1L; // TODO : 추후 시큐리티 적용 후 수정 예정
        GetQuestionDetail question = questionDbService.getQuestionDetail(questionId, userId);
        return ResponseEntity.ok().body(new SingleResponseDto<>(question));
    }

    @PatchMapping("/{questionId}/deleted")
    public ResponseEntity deleteQuestion(@PathVariable("questionId") Long questionId) {
        Long userId = 1L;
        questionService.deleteQuestion(userId, questionId);
        return ResponseEntity.ok().build();
    }
}
