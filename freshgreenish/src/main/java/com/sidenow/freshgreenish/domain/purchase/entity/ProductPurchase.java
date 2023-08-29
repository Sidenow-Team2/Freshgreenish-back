package com.sidenow.freshgreenish.domain.purchase.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sidenow.freshgreenish.domain.product.entity.Product;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product_purchase SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class ProductPurchase extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_PURCHASE_ID")
    private Long productPurchaseId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ID")
    private Purchase purchase;

    @Builder
    public ProductPurchase(Product product, Purchase purchase) {
        this.product = product;
        this.purchase = purchase;
    }
}
