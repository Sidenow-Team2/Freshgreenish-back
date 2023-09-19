package com.sidenow.freshgreenish.domain.purchase.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ProductPurchaseRepositoryImpl implements CustomProductPurchaseRepository{
    private final JPAQueryFactory queryFactory;


    public ProductPurchaseRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
