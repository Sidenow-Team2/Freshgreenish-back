package com.sidenow.freshgreenish.domain.answer.controller;

import com.sidenow.freshgreenish.domain.answer.dto.GetAnswerDetail;
import com.sidenow.freshgreenish.domain.answer.dto.PostAnswer;
import com.sidenow.freshgreenish.domain.answer.service.AnswerDbService;
import com.sidenow.freshgreenish.domain.answer.service.AnswerService;
import com.sidenow.freshgreenish.domain.dto.SingleResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final AnswerDbService answerDbService;

    @PostMapping("/question/{questionId}")
    public ResponseEntity postAnswer(@PathVariable("questionId") Long questionId,
                                     @RequestBody @Valid PostAnswer post) {
        answerService.postAnswer(questionId, post);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{answerId}")
    public ResponseEntity editAnswer(@PathVariable("answerId") Long answerId,
                                     @RequestBody @Valid PostAnswer edit) {
        answerService.editAnswer(answerId, edit);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity getAnswerDetail(@PathVariable("questionId") Long questionId) {
        GetAnswerDetail answer = answerDbService.getAnswerDetail(questionId);
        return ResponseEntity.ok().body(new SingleResponseDto<>(answer));
    }


    @PatchMapping("/{answerId}/delete")
    public ResponseEntity deleteAnswer(@PathVariable("answerId") Long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.ok().build();
    }
}
