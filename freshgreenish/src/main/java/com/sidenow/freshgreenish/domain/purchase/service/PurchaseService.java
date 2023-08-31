package com.sidenow.freshgreenish.domain.purchase.service;

import com.sidenow.freshgreenish.domain.payment.dto.PaymentResponseDto;
import com.sidenow.freshgreenish.domain.payment.entity.PaymentInfo;
import com.sidenow.freshgreenish.domain.payment.service.PaymentInfoDbService;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import com.sidenow.freshgreenish.domain.purchase.dto.ProductOrderDTO;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.service.UserDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseDbService purchaseDbService;
    private final ProductPurchaseDbService productPurchaseDbService;
    private final UserDbService userDbService;
    private final ProductDbService productDbService;
    private final PaymentInfoDbService paymentInfoDbService;

    public PaymentResponseDto checkPurchaseValue(ProductOrderDTO productOrderDTO, Long userId) {

        User user = userDbService.findById(userId);

        return purchaseSave(productOrderDTO, user);

    }

    private PaymentResponseDto purchaseSave(ProductOrderDTO productOrderDTO , User user){
        long totalAmount = 0L;
        Purchase purchase = purchaseDbService.save(productOrderDTO.toPurchaseEntity(user));

        for(ProductOrderDTO.OrderDto orderDto : productOrderDTO.getOrderDtoList()){
            Product product = productDbService.findById(orderDto.getProductId());
            productPurchaseDbService.save(orderDto.toProductPurchaseEntity(product, purchase));
            totalAmount += (long) product.getPrice() * orderDto.getCount();
        }

        PaymentInfo paymentInfo = paymentInfoDbService.save(PaymentInfo.toInitial(purchase, totalAmount));
        return paymentInfo.toTossPaymentResponseDto();
    }
}
