package com.sidenow.freshgreenish.domain.answer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepositoryImpl implements CustomAnswerRepository{
    private final JPAQueryFactory queryFactory;

    public AnswerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
