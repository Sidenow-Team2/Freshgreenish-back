package com.sidenow.freshgreenish.domain.payment.feign;

import com.sidenow.freshgreenish.domain.payment.toss.ReadyToTossPayInfo;
import com.sidenow.freshgreenish.domain.payment.toss.RequestForTossPayInfo;
import com.sidenow.freshgreenish.domain.payment.toss.TossPayReadyInfo;
import com.sidenow.freshgreenish.domain.payment.toss.TossPaySuccessInfo;
import com.sidenow.freshgreenish.domain.payment.util.PayConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "tosspay", url = "https://api.tosspayments.com", configuration = {FeignErrorConfig.class})
public interface TossPayFeignClient {
    @PostMapping(value = "/v1/payments", consumes = "application/json")
    TossPayReadyInfo readyForTossPayment(@RequestHeader(PayConstants.AUTHORIZATION) String authorization,
                                         @RequestHeader(value = "Content-Type") String contentType,
                                         @RequestBody ReadyToTossPayInfo body);

    @PostMapping(value = "/v1/payments/confirm", consumes = "application/json")
    TossPaySuccessInfo successForPayment(@RequestHeader(PayConstants.AUTHORIZATION) String authorization,
                                         @RequestHeader(value = "Content-Type") String contentType,
                                         @RequestBody RequestForTossPayInfo body);

}
