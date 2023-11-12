package com.sidenow.freshgreenish.domain.purchase.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductPurchase is a Querydsl query type for ProductPurchase
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductPurchase extends EntityPathBase<ProductPurchase> {

    private static final long serialVersionUID = -453095735L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductPurchase productPurchase = new QProductPurchase("productPurchase");

    public final com.sidenow.freshgreenish.global.utils.QAuditable _super = new com.sidenow.freshgreenish.global.utils.QAuditable(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final com.sidenow.freshgreenish.domain.product.entity.QProduct product;

    public final NumberPath<Long> productPurchaseId = createNumber("productPurchaseId", Long.class);

    public final QPurchase purchase;

    public QProductPurchase(String variable) {
        this(ProductPurchase.class, forVariable(variable), INITS);
    }

    public QProductPurchase(Path<? extends ProductPurchase> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductPurchase(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductPurchase(PathMetadata metadata, PathInits inits) {
        this(ProductPurchase.class, metadata, inits);
    }

    public QProductPurchase(Class<? extends ProductPurchase> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.sidenow.freshgreenish.domain.product.entity.QProduct(forProperty("product")) : null;
        this.purchase = inits.isInitialized("purchase") ? new QPurchase(forProperty("purchase"), inits.get("purchase")) : null;
    }

}

