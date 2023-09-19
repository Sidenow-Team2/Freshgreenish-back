package com.sidenow.freshgreenish.domain.payment.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentFailDto {
    String errorCode;
    String errorMessage;
    String orderId;
}
