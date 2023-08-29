package com.sidenow.freshgreenish.domain.payment.dto;

public class TossPaymentSuccessDto {
    String mid;
    String version;
    String paymentKey;
    String orderId;
    String orderName;
    String currency;
    String method;
    String totalAmount;
    String balanceAmount;
    String suppliedAmount;
    String vat;
    String status;
    String requestedAt;
    String approvedAt;
    String useEscrow;
    String cultureExpense;
    TossPaymentSuccessCardDto card;
    String type;
}
