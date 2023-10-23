package com.sidenow.freshgreenish.domain.purchase.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE purchase SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Purchase extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PURCHASE_ID")
    private Long purchaseId;
    private Long userId;
    private Long deliveryId;

    @Setter
    private Long addressId;

    @Setter
    private String purchaseNumber;

    @Setter
    private Integer totalCount;

    // 다건 결제
    private Long basketId;

    // 단건 결제
    private Long productId;
    private Integer count;

    @Setter
    private Integer totalPrice;

    @Setter
    private Integer totalPriceBeforeUsePoint;

    @Setter
    private Integer usedPoints;

    @Setter
    private String paymentMethod;

    @Enumerated(value = EnumType.STRING)
    private PurchaseStatus purchaseStatus;

    private Boolean isRegularDelivery;

    @OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentInfo paymentInfo;

    @JsonManagedReference
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductPurchase> productPurchase = new ArrayList<>();

    @Builder
    public Purchase(Long purchaseId, Long basketId, Long userId, Long deliveryId, Long addressId, Long productId,
                    Integer count, Integer totalCount, Integer totalPrice, Boolean isRegularDelivery,
                    Integer totalPriceBeforeUsePoint, Integer usedPoints) {
        this.purchaseId = purchaseId;
        this.basketId = basketId;
        this.userId = userId;
        this.deliveryId = deliveryId;
        this.addressId = addressId;
        this.productId = productId;
        this.count = count;
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
        this.isRegularDelivery = isRegularDelivery;
        this.totalPriceBeforeUsePoint = totalPriceBeforeUsePoint;
        this.usedPoints = usedPoints;
    }

    public void setStatus(PurchaseStatus status) {
        this.purchaseStatus = status;
    }

    public void addPaymentInfo(PaymentInfo paymentInfo) {
        if (paymentInfo.getPurchase() != this) {
            paymentInfo.setPurchase(this);
        }
        this.paymentInfo = paymentInfo;
    }

    public void addProductPurchase(ProductPurchase productPurchases) {
        if(productPurchases.getPurchase() != this) productPurchases.addPurchase(this);
        productPurchase.add(productPurchases);
    }
}
