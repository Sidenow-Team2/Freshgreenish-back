package com.sidenow.freshgreenish.domain.address.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetAddressDTO {

    private Long addressId;
    private String addressNickname;
    private String addressMain;
    private Boolean isDefaultAddress;

    public GetAddressDTO(Long addressId, String addressNickname, String addressMain, Boolean isDefaultAddress) {
        this.addressId = addressId;
        this.addressNickname = addressNickname;
        this.addressMain = addressMain;
        this.isDefaultAddress = isDefaultAddress;
    }
    public GetAddressDTO(String addressNickname, String addressMain, Boolean isDefaultAddress) {
        this.addressNickname = addressNickname;
        this.addressMain = addressMain;
        this.isDefaultAddress = isDefaultAddress;
    }
}
