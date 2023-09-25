package com.sidenow.freshgreenish.domain.address.repository;

import com.sidenow.freshgreenish.domain.address.entity.Address;
import com.sidenow.freshgreenish.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByAddressId(Long AddressId);
    List<Address> findAllByUserAndIsDefaultAddress(User user, boolean isDefaultAddress);
    Address findByUserAndIsDefaultAddressAndIsInMyPageAndDeleted(User user, boolean isDefaultAddress, boolean isInMyPage, boolean deleted);
    List<Address> findAllByUserAndIsDefaultAddressAndIsInMyPageAndDeleted(User user, boolean isDefaultAddress, boolean isInMyPage, boolean deleted);
}
