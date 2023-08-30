package com.sidenow.freshgreenish.domain.question.service;

import com.sidenow.freshgreenish.domain.question.entity.Question;
import com.sidenow.freshgreenish.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionDbService {
    private final QuestionRepository questionRepository;

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }
}
