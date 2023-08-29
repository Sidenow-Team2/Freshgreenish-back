package com.sidenow.freshgreenish.domain.payment.entity;

import com.sidenow.freshgreenish.domain.payment.dto.TossPaymentResponseDto;
import com.sidenow.freshgreenish.domain.payment.enums.PayType;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE payment_info SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PaymentInfo extends Auditable {
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

    private Long totalAmount;

    @Column(length = 2000)
    private String approvalUrl;

    @Column(length = 2000)
    private String failUrl;

    @Column(length = 2000)
    private String cancelUrl;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    private Boolean successStatus;

    private String orderName;

    private String PaymentKey;

    private String orderId;

    private String failReasons;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ID")
    private Purchase purchase;

    @Builder
    public PaymentInfo(String cid, String tid, String partnerOrderId, String partnerUserId, String itemName, Integer quantity, Long totalAmount, String approvalUrl, String failUrl, String cancelUrl, PayType payType, Boolean successStatus, String orderName, String orderId, Purchase purchase) {
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
        this.payType = payType;
        this.successStatus = successStatus;
        this.orderName = orderName;
        this.orderId = orderId;
        this.purchase = purchase;
    }

    public TossPaymentResponseDto toTossPaymentResponseDto(){
        return TossPaymentResponseDto.builder()
                .payType(payType.getDescription())
                .amount(totalAmount)
                .orderName(orderName)
                .orderId(orderId)
                .createdAt(String.valueOf(getCreatedAt()))
                .cancelYN(successStatus)
                .failReason(failReasons)
                .build();

    }
}

