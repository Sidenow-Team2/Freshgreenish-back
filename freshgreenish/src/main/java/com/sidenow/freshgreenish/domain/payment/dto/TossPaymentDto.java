package com.sidenow.freshgreenish.domain.payment.dto;

import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentDto {
    private Long amount;

    private String orderName;

    private String yourSuccessUrl;

    private String yourFailUrl;

    public PaymentInfo toEntity() {
        return PaymentInfo.builder()
                .totalAmount(amount)
                .partnerOrderId(UUID.randomUUID().toString())
                .successStatus(false)
                .build();
    }
}
