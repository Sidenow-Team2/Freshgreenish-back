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
public enum SubscriptionStatus {
    NOT_USE_SUBSCRIPTION(1, "일반 결제"),
    DURING_SUBSCRIPTION(2, "정기 결제 이용 중"),
    END_OF_SUBSCRIPTION(3, "정기 결제 종료");

    private int stepNumber;
    private String status;

    SubscriptionStatus(int stepNumber, String status) {
        this.stepNumber = stepNumber;
        this.status = status;
    }

    private static final Map<String, SubscriptionStatus> descriptions =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(SubscriptionStatus::getStatus, Function.identity())));

    public static SubscriptionStatus findByStatus(String status) {
        return Optional.ofNullable(descriptions.get(status))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STATUS_NOT_FOUND));
    }
}
