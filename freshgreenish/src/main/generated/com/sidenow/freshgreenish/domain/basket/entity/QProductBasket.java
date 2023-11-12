package com.sidenow.freshgreenish.domain.basket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductBasket is a Querydsl query type for ProductBasket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductBasket extends EntityPathBase<ProductBasket> {

    private static final long serialVersionUID = 1443157929L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductBasket productBasket = new QProductBasket("productBasket");

    public final QBasket basket;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final BooleanPath isRegular = createBoolean("isRegular");

    public final com.sidenow.freshgreenish.domain.product.entity.QProduct product;

    public final NumberPath<Long> productBasketId = createNumber("productBasketId", Long.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QProductBasket(String variable) {
        this(ProductBasket.class, forVariable(variable), INITS);
    }

    public QProductBasket(Path<? extends ProductBasket> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductBasket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductBasket(PathMetadata metadata, PathInits inits) {
        this(ProductBasket.class, metadata, inits);
    }

    public QProductBasket(Class<? extends ProductBasket> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.basket = inits.isInitialized("basket") ? new QBasket(forProperty("basket")) : null;
        this.product = inits.isInitialized("product") ? new com.sidenow.freshgreenish.domain.product.entity.QProduct(forProperty("product")) : null;
    }

}

