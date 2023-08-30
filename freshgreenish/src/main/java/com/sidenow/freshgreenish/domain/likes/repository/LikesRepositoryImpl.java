package com.sidenow.freshgreenish.domain.likes.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class LikesRepositoryImpl implements CustomLikesRepository {
    private final JPAQueryFactory queryFactory;

    public LikesRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
