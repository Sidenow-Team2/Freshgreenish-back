package com.sidenow.freshgreenish.domain.answer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidenow.freshgreenish.domain.answer.dto.GetAnswerDetail;
import com.sidenow.freshgreenish.domain.answer.dto.QGetAnswerDetail;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static com.sidenow.freshgreenish.domain.answer.entity.QAnswer.answer;

@Repository
public class AnswerRepositoryImpl implements CustomAnswerRepository{
    private final JPAQueryFactory queryFactory;

    public AnswerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public GetAnswerDetail getAnswerDetail(Long questionId) {
        return queryFactory
                .select(new QGetAnswerDetail(
                        answer.comment
                )).from(answer)
                .where(answer.questionId.eq(questionId)
                        .and(answer.deleted.eq(false)))
                .fetchOne();
    }
}
