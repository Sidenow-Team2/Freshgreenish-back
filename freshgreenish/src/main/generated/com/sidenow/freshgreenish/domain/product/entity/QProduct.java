package com.sidenow.freshgreenish.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -2096368020L;

    public static final QProduct product = new QProduct("product");

    public final com.sidenow.freshgreenish.global.utils.QAuditable _super = new com.sidenow.freshgreenish.global.utils.QAuditable(this);

    public final StringPath brand = createString("brand");

    public final StringPath capacity = createString("capacity");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath deliveryType = createString("deliveryType");

    public final StringPath detail = createString("detail");

    public final NumberPath<Integer> discountPrice = createNumber("discountPrice", Integer.class);

    public final NumberPath<Integer> discountRate = createNumber("discountRate", Integer.class);

    public final StringPath harvestSeason = createString("harvestSeason");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath notification = createString("notification");

    public final StringPath origin = createString("origin");

    public final StringPath packageType = createString("packageType");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<com.sidenow.freshgreenish.domain.basket.entity.ProductBasket, com.sidenow.freshgreenish.domain.basket.entity.QProductBasket> productBasket = this.<com.sidenow.freshgreenish.domain.basket.entity.ProductBasket, com.sidenow.freshgreenish.domain.basket.entity.QProductBasket>createList("productBasket", com.sidenow.freshgreenish.domain.basket.entity.ProductBasket.class, com.sidenow.freshgreenish.domain.basket.entity.QProductBasket.class, PathInits.DIRECT2);

    public final StringPath productDetailImage = createString("productDetailImage");

    public final StringPath productDetailImageName = createString("productDetailImageName");

    public final StringPath productFirstImage = createString("productFirstImage");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final ListPath<ProductImage, QProductImage> productImages = this.<ProductImage, QProductImage>createList("productImages", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public final StringPath productNumber = createString("productNumber");

    public final ListPath<com.sidenow.freshgreenish.domain.purchase.entity.ProductPurchase, com.sidenow.freshgreenish.domain.purchase.entity.QProductPurchase> productPurchase = this.<com.sidenow.freshgreenish.domain.purchase.entity.ProductPurchase, com.sidenow.freshgreenish.domain.purchase.entity.QProductPurchase>createList("productPurchase", com.sidenow.freshgreenish.domain.purchase.entity.ProductPurchase.class, com.sidenow.freshgreenish.domain.purchase.entity.QProductPurchase.class, PathInits.DIRECT2);

    public final NumberPath<Integer> purchaseCount = createNumber("purchaseCount", Integer.class);

    public final BooleanPath recommendation = createBoolean("recommendation");

    public final StringPath seller = createString("seller");

    public final StringPath storageMethod = createString("storageMethod");

    public final BooleanPath subscription = createBoolean("subscription");

    public final StringPath subTitle = createString("subTitle");

    public final StringPath title = createString("title");

    public final StringPath unit = createString("unit");

    public final StringPath variety = createString("variety");

    public final StringPath weight = createString("weight");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

