package com.sidenow.freshgreenish.domain.answer.service;

import com.sidenow.freshgreenish.domain.answer.entity.Answer;
import com.sidenow.freshgreenish.domain.answer.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerDbService {
    private final AnswerRepository answerRepository;

    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }
}
