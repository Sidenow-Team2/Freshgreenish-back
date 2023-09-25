package com.sidenow.freshgreenish.domain.address.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PostAddressDTO {
    private String addressNickname;
    private String recipientName;
    private String addressMain;
    private Integer postalCode;
    private String addressDetail;
    private String phoneNumber;
    private Boolean isDefaultAddress;

    public PostAddressDTO(String addressNickname, String recipientName, String addressMain, Integer postalCode, String addressDetail, String phoneNumber, Boolean isDefaultAddress) {
        this.addressNickname = addressNickname;
        this.recipientName = recipientName;
        this.addressMain = addressMain;
        this.postalCode = postalCode;
        this.addressDetail = addressDetail;
        this.phoneNumber = phoneNumber;
        this.isDefaultAddress = isDefaultAddress;
    }


    @Override
    public String toString() {
        return "AddressDTO{" +
                "addressNickname='" + addressNickname + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", addressMain='" + addressMain + '\'' +
                ", postalCode=" + postalCode +
                ", addressDetail='" + addressDetail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isDefaultAddress=" + isDefaultAddress +
                '}';
    }

    }

