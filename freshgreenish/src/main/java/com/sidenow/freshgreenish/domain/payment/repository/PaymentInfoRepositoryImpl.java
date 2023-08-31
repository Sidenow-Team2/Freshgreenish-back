package com.sidenow.freshgreenish.domain.payment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentInfoRepositoryImpl implements CustomPaymentInfoRepository {
    private final JPAQueryFactory queryFactory;


    public PaymentInfoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
