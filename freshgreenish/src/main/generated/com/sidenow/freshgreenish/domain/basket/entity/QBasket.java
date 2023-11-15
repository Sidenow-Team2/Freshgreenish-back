package com.sidenow.freshgreenish.domain.basket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBasket is a Querydsl query type for Basket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBasket extends EntityPathBase<Basket> {

    private static final long serialVersionUID = 1719882802L;

    public static final QBasket basket = new QBasket("basket");

    public final NumberPath<Long> basketId = createNumber("basketId", Long.class);

    public final ListPath<ProductBasket, QProductBasket> productBasket = this.<ProductBasket, QProductBasket>createList("productBasket", ProductBasket.class, QProductBasket.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBasket(String variable) {
        super(Basket.class, forVariable(variable));
    }

    public QBasket(Path<? extends Basket> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBasket(PathMetadata metadata) {
        super(Basket.class, metadata);
    }

}

