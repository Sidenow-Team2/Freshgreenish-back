package com.sidenow.freshgreenish.domain.likes.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidenow.freshgreenish.domain.likes.dto.GetLikesList;
import com.sidenow.freshgreenish.domain.likes.dto.QGetLikesList;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sidenow.freshgreenish.domain.likes.entity.QLikes.likes;
import static com.sidenow.freshgreenish.domain.product.entity.QProduct.product;
import static com.sidenow.freshgreenish.domain.user.entity.QUser.user;

@Repository
public class LikesRepositoryImpl implements CustomLikesRepository {
    private final JPAQueryFactory queryFactory;

    public LikesRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<GetLikesList> getLikesList(Long userId, Pageable pageable) {
        List<GetLikesList> result = queryFactory
                .select(new QGetLikesList(
                        likes.likesId,
                        product.productId,
                        product.title,
                        product.price,
                        product.discountRate,
                        product.discountPrice,
                        product.productFirstImage
                ))
                .from(likes)
                .leftJoin(product).on(likes.productId.eq(product.productId))
                .leftJoin(user).on(likes.userId.eq(user.userId))
                .where(likes.userId.eq(userId))
                .orderBy(likes.likesId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = result.size();
        return new PageImpl<>(result, pageable, total);
    }
}
