package com.sidenow.freshgreenish.domain.delivery.entity;

import com.sidenow.freshgreenish.domain.address.entity.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_ID")
    private Long deliveryId;
    private Long purchaseId;
    private Long userId;

    @Column(updatable = false)
    private LocalDateTime firstPaymentDate;
    @Setter
    private LocalDateTime thisMonthPaymentDate;
    @Setter
    private LocalDateTime nextPaymentDate;
    @Setter
    private LocalDateTime deliveryDate;

    private Boolean isRegular;
    private String paymentMethod;

    private Integer postalCode;
    private String addressMain;
    private String addressDetail;
    private String addressNickname;
    private Boolean isDefaultAddress;
    private String recipientName;
    private String phoneNumber;

    @Builder
    public Delivery(Long deliveryId, Long purchaseId, Long userId, LocalDateTime firstPaymentDate,
                    LocalDateTime thisMonthPaymentDate, LocalDateTime nextPaymentDate, Boolean isRegular,
                    LocalDateTime deliveryDate, Integer postalCode, String addressMain, String addressDetail,
                    String addressNickname, Boolean isDefaultAddress, String recipientName,
                    String phoneNumber, String paymentMethod) {
        this.deliveryId = deliveryId;
        this.purchaseId = purchaseId;
        this.userId = userId;
        this.firstPaymentDate = firstPaymentDate;
        this.thisMonthPaymentDate = thisMonthPaymentDate;
        this.nextPaymentDate = nextPaymentDate;
        this.isRegular = isRegular;
        this.deliveryDate = deliveryDate;
        this.postalCode = postalCode;
        this.addressMain = addressMain;
        this.addressDetail = addressDetail;
        this.addressNickname = addressNickname;
        this.isDefaultAddress = isDefaultAddress;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.paymentMethod = paymentMethod;
    }

    public void setAddressInfo(Address address) {
        this.postalCode = address.getPostalCode();
        this.addressMain = address.getAddressMain();
        this.addressDetail = address.getAddressDetail();
        this.addressNickname = address.getAddressNickname();
        this.isDefaultAddress = address.getIsDefaultAddress();
        this.phoneNumber = address.getPhoneNumber();
        this.recipientName = address.getRecipientName();
    }


}
