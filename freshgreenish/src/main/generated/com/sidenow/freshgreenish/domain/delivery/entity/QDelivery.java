package com.sidenow.freshgreenish.domain.delivery.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDelivery is a Querydsl query type for Delivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelivery extends EntityPathBase<Delivery> {

    private static final long serialVersionUID = -325467314L;

    public static final QDelivery delivery = new QDelivery("delivery");

    public final StringPath addressDetail = createString("addressDetail");

    public final StringPath addressMain = createString("addressMain");

    public final StringPath addressNickname = createString("addressNickname");

    public final DateTimePath<java.time.LocalDateTime> deliveryDate = createDateTime("deliveryDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> deliveryId = createNumber("deliveryId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> firstPaymentDate = createDateTime("firstPaymentDate", java.time.LocalDateTime.class);

    public final BooleanPath isDefaultAddress = createBoolean("isDefaultAddress");

    public final BooleanPath isRegular = createBoolean("isRegular");

    public final DateTimePath<java.time.LocalDateTime> nextPaymentDate = createDateTime("nextPaymentDate", java.time.LocalDateTime.class);

    public final StringPath paymentMethod = createString("paymentMethod");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Integer> postalCode = createNumber("postalCode", Integer.class);

    public final NumberPath<Long> purchaseId = createNumber("purchaseId", Long.class);

    public final StringPath recipientName = createString("recipientName");

    public final DateTimePath<java.time.LocalDateTime> thisMonthPaymentDate = createDateTime("thisMonthPaymentDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QDelivery(String variable) {
        super(Delivery.class, forVariable(variable));
    }

    public QDelivery(Path<? extends Delivery> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDelivery(PathMetadata metadata) {
        super(Delivery.class, metadata);
    }

}

