package com.sidenow.freshgreenish.domain.address.controller;

import com.sidenow.freshgreenish.domain.address.dto.GetAddressDTO;
import com.sidenow.freshgreenish.domain.address.dto.PostAddressDTO;
import com.sidenow.freshgreenish.domain.address.service.AddressService;
import com.sidenow.freshgreenish.domain.dto.SingleResponseDto;
import com.sidenow.freshgreenish.domain.purchase.dto.AddressInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/mypage/address")
    public ResponseEntity getAddress(@AuthenticationPrincipal OAuth2User oauth) {
        List<GetAddressDTO> dtos = addressService.readAddress(oauth);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/mypage/address/create")
    public ResponseEntity postAddress(@AuthenticationPrincipal OAuth2User oauth,
                                      @RequestBody PostAddressDTO dto) {
        Boolean isInMyPage = true;
        addressService.createAddress(oauth, dto, isInMyPage);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/mypage/address/update")
    public ResponseEntity patchAddress(@AuthenticationPrincipal OAuth2User oauth,
                                       @RequestParam("addressId") Long addressId,
                                       @RequestBody PostAddressDTO dto) {
        addressService.updateAddress(oauth, addressId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/mypage/address/delete")
    public ResponseEntity deleteAddress(@RequestParam("addressId") Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/purchase/{purchaseId}/address/create")
    public ResponseEntity createAddressForPurchase(@PathVariable("purchaseId") Long purchaseId,
                                                   @RequestBody @Valid PostAddressDTO post,
                                                   @AuthenticationPrincipal OAuth2User oauth) {
        addressService.addAddressForPurchase(oauth, purchaseId, post);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/purchase/{purchaseId}/address/{addressId}")
    public ResponseEntity getAddressIdForPurchase(@PathVariable("purchaseId") Long purchaseId,
                                                  @PathVariable("addressId") Long addressId,
                                                  @AuthenticationPrincipal OAuth2User oauth) {
        addressService.addAddressIdForPurchase(oauth, purchaseId, addressId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/purchase/{purchaseId}/address")
    public ResponseEntity getAddressForPurchase(@PathVariable("purchaseId") Long purchaseId,
                                                @AuthenticationPrincipal OAuth2User oauth) {
        AddressInfo address = addressService.getAddressForPurchase(oauth, purchaseId);
        return ResponseEntity.ok().body(new SingleResponseDto<>(address));
    }
}
