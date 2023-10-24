package com.sidenow.freshgreenish.domain.delivery.service;

import com.sidenow.freshgreenish.domain.address.entity.Address;
import com.sidenow.freshgreenish.domain.delivery.entity.Delivery;
import com.sidenow.freshgreenish.domain.delivery.repository.DeliveryRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryDbService {
    private final DeliveryRepository deliveryRepository;

    public void saveDelivery(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    public Delivery saveAndReturnDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Delivery ifExistsReturnDelivery(Long purchaseId) {
        return deliveryRepository.findByPurchaseId(purchaseId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
    }

    public Delivery findOrCreateDelivery(Long purchaseId, Long userId, LocalDateTime paymentDate, String method, Address address) {
        if (deliveryRepository.findByPurchaseId(purchaseId).isPresent()) {
            return deliveryRepository.findByPurchaseId(purchaseId).get();
        }

        Delivery delivery =  Delivery.builder()
                .purchaseId(purchaseId)
                .userId(userId)
                .firstPaymentDate(paymentDate)
                .isRegular(true)
                .paymentMethod(method)
                .build();

        delivery.setAddressInfo(address);
        Delivery newDelivery = saveAndReturnDelivery(delivery);

        return newDelivery;
    }
}
