package com.sidenow.freshgreenish.domain.payment.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sidenow.freshgreenish.domain.payment.toss.ReadyToTossPayInfo;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    /* ---------- 카카오 ---------- */

    private String cid;
    private String tid;
    private String partnerOrderId;
    private String partnerUserId;
    private String itemName;
    private Integer totalAmount;
    private Integer valAmount;
    private Integer taxFreeAmount;
    private String approvalUrl;
    private String cancelUrl;

    /* ---------- 공통 ---------- */

    private String failUrl;

    /* ---------- 토스 ---------- */

    private Integer amount;
    private String orderId;
    private String orderName;
    private String successUrl;
    private String paymentKey;

    /* -------------------- */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ID")
    @JsonManagedReference
    private Purchase purchase;

    @Builder
    public PaymentInfo(Purchase purchase) {
        this.purchase = purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public void setTossPaymentInfo(ReadyToTossPayInfo body) {
        this.failUrl = body.getFailUrl();
        this.amount = body.getAmount();
        this.orderId = body.getOrderId();
        this.orderName = body.getOrderName();
        this.successUrl = body.getSuccessUrl();
    }
}

