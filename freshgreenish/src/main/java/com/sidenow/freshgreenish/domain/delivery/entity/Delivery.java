package com.sidenow.freshgreenish.domain.delivery.entity;

import com.sidenow.freshgreenish.domain.purchase.entity.Purchase;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE delivery SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Delivery extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_INFO_ID")
    private Long deliveryId;

    private LocalDateTime paymentDate;

    private Boolean isRegular;

    private LocalDateTime deliveryDate;

    @OneToOne
    private Purchase purchase;

    @Builder
    public Delivery(LocalDateTime paymentDate, Boolean isRegular, LocalDateTime deliveryDate, Purchase purchase) {
        this.paymentDate = paymentDate;
        this.isRegular = isRegular;
        this.deliveryDate = deliveryDate;
        this.purchase = purchase;
    }
}
