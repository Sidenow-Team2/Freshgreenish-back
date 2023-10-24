package com.sidenow.freshgreenish.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetMyInfo {

    private String filePath;
    private String nickname;
    private Integer saved_money;
    private List<LocalDateTime> regularPaymentDates;
    private List<LocalDateTime> regularDeliveryDates;

    public GetMyInfo(String filePath, String nickname, Integer saved_money) {
        this.filePath = filePath;
        this.nickname = nickname;
        this.saved_money = saved_money;
    }

    public GetMyInfo(String filePath, String nickname, Integer saved_money, List<LocalDateTime> regularPaymentDates, List<LocalDateTime> regularDeliveryDates) {
        this.filePath = filePath;
        this.nickname = nickname;
        this.saved_money = saved_money;
        this.regularPaymentDates = regularPaymentDates;
        this.regularDeliveryDates = regularDeliveryDates;
    }
}
