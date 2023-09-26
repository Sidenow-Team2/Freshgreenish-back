package com.sidenow.freshgreenish.domain.payment.dto;

import lombok.*;

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

}
