package com.sidenow.freshgreenish.domain.address.service;

import com.sidenow.freshgreenish.domain.address.entity.Address;
import com.sidenow.freshgreenish.domain.address.repository.AddressRepository;
import com.sidenow.freshgreenish.domain.purchase.service.PurchaseDbService;
import com.sidenow.freshgreenish.domain.user.service.UserDbService;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressDbService {
    private final AddressRepository addressRepository;
    private final UserDbService userDbService;
    private final PurchaseDbService purchaseDbService;

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    public Address saveAndReturnAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address ifExistsReturnAddress(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ADDRESS_NOT_FOUND));
    }

    public Optional<Address> ifExistsReturnOptionalAddress(Long addressId) {
        return addressRepository.findById(addressId);
    }
}
