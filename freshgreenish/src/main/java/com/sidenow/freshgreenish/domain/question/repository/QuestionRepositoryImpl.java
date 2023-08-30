package com.sidenow.freshgreenish.domain.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryImpl implements CustomQuestionRepository {
    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
