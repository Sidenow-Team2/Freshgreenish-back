package com.sidenow.freshgreenish.domain.payment.entity;

import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_INFO_ID")
    private Long paymentInfoId;

    @Column(length = 2000)
    private String cid;

    @Column(length = 2000)
    private String tid;

    @Column(length = 2000)
    private String partnerOrderId;

    @Column(length = 20)
    private String partnerUserId;

    @Column(length = 20)
    private String itemName;

    private Integer quantity;

    private Integer totalAmount;

    @Column(length = 2000)
    private String approvalUrl;

    @Column(length = 2000)
    private String failUrl;

    @Column(length = 2000)
    private String cancelUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ID")
    private Purchase purchase;

    @Builder
    public PaymentInfo(String cid, String tid, String partnerOrderId, String partnerUserId, String itemName, Integer quantity, Integer totalAmount, String approvalUrl, String failUrl, String cancelUrl, Purchase purchase) {
        this.cid = cid;
        this.tid = tid;
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.approvalUrl = approvalUrl;
        this.failUrl = failUrl;
        this.cancelUrl = cancelUrl;
        this.purchase = purchase;
    }
}

