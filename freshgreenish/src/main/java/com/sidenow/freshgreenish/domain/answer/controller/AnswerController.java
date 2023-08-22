package com.sidenow.freshgreenish.domain.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/answer")
public class AnswerController {
    @PostMapping("/question/{question-id}")
    public ResponseEntity postAnswer(@PathVariable("question-id") Long questionId
                                    /* @RequestBody @Valid PostAnswer post */) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{answer-id}")
    public ResponseEntity editAnswer(@PathVariable("answer-id") Long answerId
                                    /* @RequestBody @Valid editAnswer edit */) {
        return ResponseEntity.ok().build();
    }

/*
    @GetMapping("/product/{product-id}")
    public ResponseEntity getAnswerDetail(@PathVariable("product-id") Long productId) {
        return ResponseEntity.ok().build();
    }
*/

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") Long answerId) {
        return ResponseEntity.noContent().build();
    }
}
