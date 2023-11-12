package com.sidenow.freshgreenish.domain.address.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAddress is a Querydsl query type for Address
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddress extends EntityPathBase<Address> {

    private static final long serialVersionUID = -366122036L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAddress address = new QAddress("address");

    public final com.sidenow.freshgreenish.global.utils.QAuditable _super = new com.sidenow.freshgreenish.global.utils.QAuditable(this);

    public final StringPath addressDetail = createString("addressDetail");

    public final NumberPath<Long> addressId = createNumber("addressId", Long.class);

    public final StringPath addressMain = createString("addressMain");

    public final StringPath addressNickname = createString("addressNickname");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final BooleanPath isDefaultAddress = createBoolean("isDefaultAddress");

    public final BooleanPath isInMyPage = createBoolean("isInMyPage");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Integer> postalCode = createNumber("postalCode", Integer.class);

    public final StringPath recipientName = createString("recipientName");

    public final com.sidenow.freshgreenish.domain.user.entity.QUser user;

    public QAddress(String variable) {
        this(Address.class, forVariable(variable), INITS);
    }

    public QAddress(Path<? extends Address> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAddress(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAddress(PathMetadata metadata, PathInits inits) {
        this(Address.class, metadata, inits);
    }

    public QAddress(Class<? extends Address> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.sidenow.freshgreenish.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

