package com.sidenow.freshgreenish.domain.payment.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private Long totalAmount;
    private String orderId;
    private Long purchaseId;
    private String successUrl;
    private String failUrl;
}
