package com.sidenow.freshgreenish.domain.user.service;

import com.sidenow.freshgreenish.domain.address.dto.PostAddressDTO;
import com.sidenow.freshgreenish.domain.address.entity.Address;
import com.sidenow.freshgreenish.domain.address.repository.AddressRepository;
import com.sidenow.freshgreenish.domain.delivery.entity.Delivery;
import com.sidenow.freshgreenish.domain.delivery.repository.DeliveryRepository;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.purchase.repository.PurchaseRepository;
import com.sidenow.freshgreenish.domain.purchase.service.PurchaseDbService;
import com.sidenow.freshgreenish.domain.user.dto.GetMyInfo;
import com.sidenow.freshgreenish.domain.user.dto.UserImageVO;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.domain.user.repository.UserRepository;
import com.sidenow.freshgreenish.global.exception.BusinessLogicException;
import com.sidenow.freshgreenish.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sidenow.freshgreenish.domain.purchase.entity.SubscriptionStatus.DURING_SUBSCRIPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyInfoService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserImageFileHandler fileHandler;
    private final PurchaseDbService purchaseDbService;
    private final PurchaseRepository purchaseRepository;
    private final DeliveryRepository deliveryRepository;

    public GetMyInfo getUserInfo(OAuth2User oauth) {
        User user = findUser(oauth);
        Long userId = user.getUserId();
        GetMyInfo infodto = new GetMyInfo();

        if (purchaseDbService.ifExitsSubscriptionReturnBoolean(userId)){ // 정기구독 존재 여부
            List<LocalDateTime> regularPaymentDate = new ArrayList<>();
            List<LocalDateTime> regularDeliveryDate = new ArrayList<>();
            List<Purchase> PurchaseIDList = purchaseRepository.findAllByUserIdAndSubscriptionStatus(userId, DURING_SUBSCRIPTION);
            for (Purchase p : PurchaseIDList){
                Long purchaseId = p.getPurchaseId();
                Optional<Delivery> optionalDelivery = deliveryRepository.findByPurchaseId(purchaseId);
                Delivery delivery = optionalDelivery.get();

                regularPaymentDate.add(delivery.getThisMonthPaymentDate());
                regularDeliveryDate.add(delivery.getDeliveryDate());
            }

            infodto = new GetMyInfo(user.getFilePath(), user.getNickname(), user.getSaved_money(),
                    regularPaymentDate, regularDeliveryDate);
        }
        else{
            infodto = new GetMyInfo(user.getFilePath(), user.getNickname(), user.getSaved_money());
        }
        return infodto;
    }

    public void changeNickname(OAuth2User oauth, String nickname) {
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

    public void changeImage(OAuth2User oauth, MultipartFile filepath) throws Exception {
        User user = findUser(oauth);
        UserImageVO imageVO = fileHandler.parseImageURL(filepath);
        if (imageVO == null){
            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND);
        } else{
            user.setFilePath(imageVO.getStoredFilePath());
            userRepository.save(user);
        }
    }

    public User findUser(OAuth2User oauth){
        String userEmail = oauth.getAttribute("email");
        return userRepository.findByEmail(userEmail)
                .orElseThrow(
                        NullPointerException::new
                );
    }



}
