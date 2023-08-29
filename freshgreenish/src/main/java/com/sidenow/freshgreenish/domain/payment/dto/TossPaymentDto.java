package com.sidenow.freshgreenish.domain.payment.dto;

import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.payment.enums.PayType;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentDto {
    private PayType payType;

    private Long amout;

    private String orderName;

    private String yourSuccessUrl;

    private String yourFailUrl;

    public PaymentInfo toEntity() {
        return PaymentInfo.builder()
                .payType(payType)
                .totalAmount(amout)
                .partnerOrderId(UUID.randomUUID().toString())
                .successStatus(false)
                .orderName(orderName)
                .build();
    }
}
