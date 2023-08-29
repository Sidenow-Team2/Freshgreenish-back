package com.sidenow.freshgreenish.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@SQLDelete(sql = "UPDATE product_image SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class ProductImage extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_IMAGE_ID")
    private Long productImageId;
    private String originFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Builder
    public ProductImage(Long productImageId, String originFileName, String fileName, String filePath, Long fileSize) {
        this.productImageId = productImageId;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void addProduct(Product product) {
        this.product = product;
    }
}
