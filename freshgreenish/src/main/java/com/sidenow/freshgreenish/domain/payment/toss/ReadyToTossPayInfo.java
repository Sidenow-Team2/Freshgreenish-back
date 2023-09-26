package com.sidenow.freshgreenish.domain.payment.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadyToTossPayInfo {
    private Integer amount;
    private String failUrl;
    private String method;
    private String orderId;
    private String orderName;
    private String successUrl;
}
