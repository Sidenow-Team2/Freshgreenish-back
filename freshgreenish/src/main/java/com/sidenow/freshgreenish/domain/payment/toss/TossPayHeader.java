package com.sidenow.freshgreenish.domain.payment.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPayHeader {
    private String adminKey;
    private String contentType;
}
