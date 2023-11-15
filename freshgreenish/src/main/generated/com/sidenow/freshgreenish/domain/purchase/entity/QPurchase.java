package com.sidenow.freshgreenish.domain.purchase.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchase is a Querydsl query type for Purchase
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchase extends EntityPathBase<Purchase> {

    private static final long serialVersionUID = 133159784L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchase purchase = new QPurchase("purchase");

    public final com.sidenow.freshgreenish.global.utils.QAuditable _super = new com.sidenow.freshgreenish.global.utils.QAuditable(this);

    public final NumberPath<Long> addressId = createNumber("addressId", Long.class);

    public final NumberPath<Long> basketId = createNumber("basketId", Long.class);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> deliveryId = createNumber("deliveryId", Long.class);

    public final BooleanPath isRegularDelivery = createBoolean("isRegularDelivery");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final com.sidenow.freshgreenish.domain.payment.entity.QPaymentInfo paymentInfo;

    public final StringPath paymentMethod = createString("paymentMethod");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final ListPath<ProductPurchase, QProductPurchase> productPurchase = this.<ProductPurchase, QProductPurchase>createList("productPurchase", ProductPurchase.class, QProductPurchase.class, PathInits.DIRECT2);

    public final NumberPath<Long> purchaseId = createNumber("purchaseId", Long.class);

    public final StringPath purchaseNumber = createString("purchaseNumber");

    public final EnumPath<PurchaseStatus> purchaseStatus = createEnum("purchaseStatus", PurchaseStatus.class);

    public final EnumPath<SubscriptionStatus> subscriptionStatus = createEnum("subscriptionStatus", SubscriptionStatus.class);

    public final NumberPath<Integer> totalCount = createNumber("totalCount", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public final NumberPath<Integer> totalPriceBeforeUsePoint = createNumber("totalPriceBeforeUsePoint", Integer.class);

    public final NumberPath<Integer> usedPoints = createNumber("usedPoints", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QPurchase(String variable) {
        this(Purchase.class, forVariable(variable), INITS);
    }

    public QPurchase(Path<? extends Purchase> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchase(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchase(PathMetadata metadata, PathInits inits) {
        this(Purchase.class, metadata, inits);
    }

    public QPurchase(Class<? extends Purchase> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.paymentInfo = inits.isInitialized("paymentInfo") ? new com.sidenow.freshgreenish.domain.payment.entity.QPaymentInfo(forProperty("paymentInfo"), inits.get("paymentInfo")) : null;
    }

}

