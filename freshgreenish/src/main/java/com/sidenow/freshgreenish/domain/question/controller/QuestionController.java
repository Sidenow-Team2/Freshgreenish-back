package com.sidenow.freshgreenish.domain.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    @PostMapping
    public ResponseEntity postQuestion(/* @RequestBody @Valid PostQuestion post */) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{question-id}")
    public ResponseEntity editQuestion(@PathVariable("product-id") Long productId
                                      /* @RequestBody @Valid editQuestion edit */) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getAllQuestion(Pageable pageable) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("{question-id}")
    public ResponseEntity getQuestionDetail(@PathVariable("question-id") Long questionId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") Long questionId) {
        return ResponseEntity.noContent().build();
    }
}
