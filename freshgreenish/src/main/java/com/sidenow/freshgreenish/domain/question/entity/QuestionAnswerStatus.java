package com.sidenow.freshgreenish.domain.question.entity;

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
public enum QuestionAnswerStatus {
    WAITING_FOR_ANSWER(1, "답변 대기"),
    END_OF_ANSWER(2, "답변 완료");

    private int stepNumber;
    private String status;

    QuestionAnswerStatus(int stepNumber, String status) {
        this.stepNumber = stepNumber;
        this.status = status;
    }

    private static final Map<String, QuestionAnswerStatus> descriptions =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(QuestionAnswerStatus::getStatus, Function.identity())));

    public static QuestionAnswerStatus findByStatus(String status) {
        return Optional.ofNullable(descriptions.get(status))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_IMAGE));
    }
}
