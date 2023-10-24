package com.sidenow.freshgreenish.domain.purchase.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sidenow.freshgreenish.domain.address.entity.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressInfo {
    private Long addressId;
    private Integer postalCode;
    private String addressMain;
    private String addressDetail;
    private String addressNickname;
    private Boolean isInMyPage;
    private Boolean isDefaultAddress;
    private String recipientName;
    private String phoneNumber;

    @Builder
    @QueryProjection
    public AddressInfo(Address address) {
        this.addressId = address.getAddressId();
        this.postalCode = address.getPostalCode();
        this.addressMain = address.getAddressMain();
        this.addressDetail = address.getAddressDetail();
        this.addressNickname = address.getAddressNickname();
        this.isInMyPage = address.getIsInMyPage();
        this.isDefaultAddress = address.getIsDefaultAddress();
        this.recipientName = address.getRecipientName();
        this.phoneNumber = address.getPhoneNumber();
    }
}
