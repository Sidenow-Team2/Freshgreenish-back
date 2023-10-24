package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sidenow.freshgreenish.domain.address.entity.Address;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetSubscriptionOnMyPage {
    private Long purchaseId;
    private Long deliveryId;
    private String purchaseNumber;
    private String orderName;
    private Integer usedPoints;
    private Integer totalPrice;
    private String purchaseStatus;

    private LocalDateTime firstPaymentDate;
    private LocalDateTime thisMonthPaymentDate;
    private LocalDateTime nextPaymentDate;
    private LocalDateTime deliveryDate;

    private Boolean isRegular;
    private String paymentMethod;

    private Integer postalCode;
    private String addressMain;
    private String addressDetail;
    private String addressNickname;
    private Boolean isInMyPage;
    private Boolean isDefaultAddress;
    private String recipientName;
    private String phoneNumber;

    private Long productId;
    private String title;
    private String productFirstImage;
    private Integer count;
    private Integer discountedPrice;

    @Builder
    @QueryProjection
    public GetSubscriptionOnMyPage(Purchase purchase, String orderName, Address address, String title,
                                   String productFirstImage, Integer discountedPrice, Long productId,
                                   Long deliveryId, LocalDateTime deliveryDate, LocalDateTime firstPaymentDate,
                                   LocalDateTime thisMonthPaymentDate, LocalDateTime nextPaymentDate) {
        this.purchaseId = purchase.getPurchaseId();
        this.deliveryId = deliveryId;

        this.purchaseNumber = purchase.getPurchaseNumber();
        this.orderName = orderName;
        this.usedPoints = purchase.getUsedPoints();
        this.totalPrice = purchase.getTotalPrice();

        this.deliveryDate = deliveryDate;
        this.firstPaymentDate = firstPaymentDate;
        this.thisMonthPaymentDate = thisMonthPaymentDate;
        this.nextPaymentDate = nextPaymentDate;
        this.paymentMethod = purchase.getPaymentMethod();

        this.postalCode = address.getPostalCode();
        this.addressMain = address.getAddressMain();
        this.addressDetail = address.getAddressDetail();
        this.addressNickname = address.getAddressNickname();
        this.isInMyPage = address.getIsInMyPage();
        this.isDefaultAddress = address.getIsDefaultAddress();
        this.recipientName = address.getRecipientName();
        this.phoneNumber = address.getPhoneNumber();
        this.productId = productId;
        this.title = title;
        this.productFirstImage = productFirstImage;
        this.count = purchase.getCount();
        this.discountedPrice = discountedPrice;
        this.purchaseStatus = purchase.getPurchaseStatus().getStatus();
    }
}
