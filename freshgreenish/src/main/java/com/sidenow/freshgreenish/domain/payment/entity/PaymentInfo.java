package com.sidenow.freshgreenish.domain.payment.entity;

import com.sidenow.freshgreenish.domain.payment.dto.PaymentResponseDto;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE payment_info SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString
public class PaymentInfo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_INFO_ID")
    private Long paymentInfoId;

    @Column(length = 2000)
    private String cid; // Mid

    @Column(length = 2000)
    private String tid; // payment

    @Column(length = 2000)
    private String partnerOrderId; // orderId

    @Column(length = 20)
    private String partnerUserId;

    private Long totalAmount;

    @Column(length = 2000)
    private String approvalUrl;

    @Column(length = 2000)
    private String failUrl;

    @Column(length = 2000)
    private String cancelUrl;

    private Boolean successStatus;

    private String failReasons;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ID")
    private Purchase purchase;

    @Builder
    public PaymentInfo(String cid, String tid, String partnerOrderId, String partnerUserId, Long totalAmount, String approvalUrl, String failUrl, String cancelUrl, Boolean successStatus, String failReasons, Purchase purchase) {
        this.cid = cid;
        this.tid = tid;
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        this.totalAmount = totalAmount;
        this.approvalUrl = approvalUrl;
        this.failUrl = failUrl;
        this.cancelUrl = cancelUrl;
        this.successStatus = successStatus;
        this.failReasons = failReasons;
        this.purchase = purchase;
    }

    public void setTossPayment(String cid) {
        this.cid = cid;
    }

    public PaymentResponseDto toTossPaymentResponseDto(){
        return PaymentResponseDto.builder()
                .totalAmount(totalAmount)
                .orderId(this.partnerOrderId)
                .purchaseId(purchase.getPurchaseId())
                .build();
    }

    public static PaymentInfo toInitial(Purchase purchase, Long totalAmount){
        return PaymentInfo.builder()
                .totalAmount(totalAmount)
                .partnerOrderId(UUID.randomUUID().toString())
                .purchase(purchase)
                .build();
    }
}

