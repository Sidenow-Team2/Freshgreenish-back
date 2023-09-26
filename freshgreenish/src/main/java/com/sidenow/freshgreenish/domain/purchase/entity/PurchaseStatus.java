package com.sidenow.freshgreenish.domain.purchase.entity;

import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum PurchaseStatus {
    PAY_IN_PROGRESS(1, "결제 대기 중"),
    PAY_SUCCESS(2, "결제 완료"),
    DELIVERY_IN_PROGRESS(3, "배송 준비 중"),
    DURING_DELIVERY(4, "배송 중"),
    DELIVERY_SUCCESS(5, "배송 완료"),
    REFUND_PAYMENT(6, "환불 완료");

    private int stepNumber;
    private String status;

    PurchaseStatus(int stepNumber, String status) {
        this.stepNumber = stepNumber;
        this.status = status;
    }

    private static final Map<String, PurchaseStatus> descriptions =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(PurchaseStatus::getStatus, Function.identity())));

    public static PurchaseStatus findByStatus(String status) {
        return Optional.ofNullable(descriptions.get(status))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATUS_NOT_FOUND));
    }
}
