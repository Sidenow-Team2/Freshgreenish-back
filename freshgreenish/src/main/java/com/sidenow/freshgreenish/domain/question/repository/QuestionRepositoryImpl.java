package com.sidenow.freshgreenish.domain.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidenow.freshgreenish.domain.question.dto.*;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sidenow.freshgreenish.domain.answer.entity.QAnswer.answer;
import static com.sidenow.freshgreenish.domain.product.entity.QProduct.product;
import static com.sidenow.freshgreenish.domain.question.entity.QQuestion.question;
import static com.sidenow.freshgreenish.domain.user.entity.QUser.user;

@Repository
public class QuestionRepositoryImpl implements CustomQuestionRepository {
    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<GetQuestionOnMyPage> getQuestionOnMyPage(Long userId, Pageable pageable) {
        List<GetQuestionOnMyPage> results = queryFactory
                .select(new QGetQuestionOnMyPage(
                        question,
                        product.title,
                        product.productDetailImage,
                        answer.comment
                )).from(question)
                .distinct()
                .leftJoin(product).on(question.productId.eq(product.productId))
                .leftJoin(answer).on(answer.questionId.eq(question.questionId))
                .where(question.userId.eq(userId))
                .orderBy(question.questionId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<GetQuestionOnQnAPage> getQuestionOnQnAPage(Pageable pageable) {
        List<GetQuestionOnQnAPage> results = queryFactory
                .select(new QGetQuestionOnQnAPage(
                        question,
                        user.nickname,
                        user.userId,
                        answer.comment
                )).from(question)
                .distinct()
                .leftJoin(user).on(question.userId.eq(user.userId))
                .leftJoin(answer).on(answer.questionId.eq(question.questionId))
                .where(question.deleted.eq(false))
                .orderBy(question.questionId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = results.size();
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public GetQuestionDetail getQuestionDetail(Long questionId, Long userId) {
        return queryFactory
                .select(new QGetQuestionDetail(
                        question,
                        product.title,
                        product.productFirstImage
                )).from(question)
                .leftJoin(product).on(question.productId.eq(product.productId))
                .distinct()
                .where(question.userId.eq(userId)
                        .and(question.questionId.eq(questionId))
                        .and(product.deleted.eq(false)))
                .fetchOne();
    }

    @Override
    public Boolean isDeleted(Long questionId) {
        return queryFactory
                .select(question.deleted)
                .from(question)
                .where(question.questionId.eq(questionId))
                .fetchOne();
    }
}
