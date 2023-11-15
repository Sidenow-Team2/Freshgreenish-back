package com.sidenow.freshgreenish.domain.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentInfo is a Querydsl query type for PaymentInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentInfo extends EntityPathBase<PaymentInfo> {

    private static final long serialVersionUID = 1646098522L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentInfo paymentInfo = new QPaymentInfo("paymentInfo");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath approvalUrl = createString("approvalUrl");

    public final StringPath cancelUrl = createString("cancelUrl");

    public final StringPath cid = createString("cid");

    public final DateTimePath<java.time.LocalDateTime> deliveryDate = createDateTime("deliveryDate", java.time.LocalDateTime.class);

    public final StringPath failUrl = createString("failUrl");

    public final StringPath itemName = createString("itemName");

    public final StringPath orderId = createString("orderId");

    public final StringPath orderName = createString("orderName");

    public final StringPath partnerOrderId = createString("partnerOrderId");

    public final StringPath partnerUserId = createString("partnerUserId");

    public final DateTimePath<java.time.LocalDateTime> paymentDate = createDateTime("paymentDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> paymentId = createNumber("paymentId", Long.class);

    public final StringPath paymentKey = createString("paymentKey");

    public final com.sidenow.freshgreenish.domain.purchase.entity.QPurchase purchase;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final StringPath sid = createString("sid");

    public final StringPath successUrl = createString("successUrl");

    public final NumberPath<Integer> taxFreeAmount = createNumber("taxFreeAmount", Integer.class);

    public final StringPath tid = createString("tid");

    public final NumberPath<Integer> totalAmount = createNumber("totalAmount", Integer.class);

    public final NumberPath<Integer> valAmount = createNumber("valAmount", Integer.class);

    public QPaymentInfo(String variable) {
        this(PaymentInfo.class, forVariable(variable), INITS);
    }

    public QPaymentInfo(Path<? extends PaymentInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentInfo(PathMetadata metadata, PathInits inits) {
        this(PaymentInfo.class, metadata, inits);
    }

    public QPaymentInfo(Class<? extends PaymentInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.purchase = inits.isInitialized("purchase") ? new com.sidenow.freshgreenish.domain.purchase.entity.QPurchase(forProperty("purchase"), inits.get("purchase")) : null;
    }

}

