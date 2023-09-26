package com.sidenow.freshgreenish.domain.user.service;

import com.sidenow.freshgreenish.domain.address.dto.PostAddressDTO;
import com.sidenow.freshgreenish.domain.address.entity.Address;
import com.sidenow.freshgreenish.domain.address.repository.AddressRepository;
import com.sidenow.freshgreenish.domain.address.service.AddressService;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.repository.MyInfoRepository;
import com.sidenow.freshgreenish.domain.user.repository.UserRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyInfoService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public void changeNickname(@AuthenticationPrincipal OAuth2User oauth, String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_NICKNAME);
        } else{
            User user = findUser(oauth);
            user.setNickname(nickname);
            userRepository.save(user);
        }
    }

    public void changeAddress(OAuth2User oauth, PostAddressDTO dto) {
        User user = findUser(oauth);
        Address address = addressRepository.findByUserAndIsDefaultAddress(user, true);
        address.updateAddress(dto.getPostalCode(), dto.getAddressMain(), dto.getAddressDetail(),
                dto.getAddressNickname(), true, dto.getRecipientName(), dto.getPhoneNumber());
        addressRepository.save(address);
    }

    public User findUser(OAuth2User oauth){
        String userEmail = oauth.getAttribute("email");
        return userRepository.findByEmail(userEmail)
                .orElseThrow(
                        NullPointerException::new
                );
    }
}
