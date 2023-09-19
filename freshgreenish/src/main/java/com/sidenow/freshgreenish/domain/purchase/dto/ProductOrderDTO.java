package com.sidenow.freshgreenish.domain.purchase.dto;

import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.domain.purchase.entity.ProductPurchase;
import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.domain.user.entity.User;
import lombok.*;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDTO {

    private List<OrderDto> orderDtoList;
    private Boolean regularDeliveryStatus;

    @Getter
    public static class OrderDto {
        private Long productId;
        private Integer count;


        public ProductPurchase toProductPurchaseEntity(Product product, Purchase purchase) {
            return ProductPurchase.builder()
                    .product(product)
                    .purchase(purchase)
                    .count(this.count)
                    .build();
        }
    }

    public Purchase toPurchaseEntity(User user){
        return Purchase.builder()
                .user(user)
                .regularDeliveryStatus(this.regularDeliveryStatus)
                .build();
    }



}
