package com.sidenow.freshgreenish.domain.purchase.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseRepositoryImpl {

    private final JPAQueryFactory queryFactory;


    public PurchaseRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
