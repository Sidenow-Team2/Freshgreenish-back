package com.sidenow.freshgreenish.domain.answer.repository;

import com.sidenow.freshgreenish.domain.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long>, CustomAnswerRepository {
}
