package com.sidenow.freshgreenish.domain.likes.service;

import com.sidenow.freshgreenish.domain.likes.dto.GetLikesList;
import com.sidenow.freshgreenish.domain.likes.entity.Likes;
import com.sidenow.freshgreenish.domain.likes.repository.LikesRepository;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.product.service.ProductDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final ProductDbService productDbService;

    public boolean isUserLikes(Long userId, Long productId) {
        if (userId == null) return false;
        Optional<Likes> findLikes = likesRepository.findByUserIdAndProductId(userId, productId);
        return findLikes.isPresent();
    }

    public boolean changeLikesStatus(Long userId, Long productId) {
        Optional<Likes> findLikes = likesRepository.findByUserIdAndProductId(userId, productId);
        Product findProduct = productDbService.ifExistsReturnProduct(productId);
        if (findLikes.isPresent()) {
            likesRepository.delete(findLikes.get());
            findProduct.reduceLikeCount();
            productDbService.saveProduct(findProduct);
            return false;
        }

        likesRepository.save(
                Likes.builder()
                        .userId(userId)
                        .productId(productId)
                        .build());
        findProduct.addLikeCount();
        productDbService.saveProduct(findProduct);
        return true;
    }

    public Page<GetLikesList> findLikesList(Long userId, Pageable pageable) {
        return likesRepository.getLikesList(userId, pageable);
    }
}
