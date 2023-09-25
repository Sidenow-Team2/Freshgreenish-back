package com.sidenow.freshgreenish.domain.address.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE address SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Address extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long addressId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private Integer postalCode;

    @Column(length = 200)
    private String addressMain;

    @Column(length = 200)
    private String addressDetail;

    @Column(length = 20)
    private String addressNickname;

    private Boolean isInMyPage;

    private Boolean isDefaultAddress;
    @Column(length = 10)
    private String recipientName;

    private String phoneNumber;

    @Builder
    public Address(User user, Integer postalCode, String addressMain, String addressDetail, String addressNickname, Boolean isInMyPage, Boolean isDefaultAddress, String recipientName, String phoneNumber) {
        this.user = user;
        this.postalCode = postalCode;
        this.addressMain = addressMain;
        this.addressDetail = addressDetail;
        this.addressNickname = addressNickname;
        this.isInMyPage = isInMyPage;
        this.isDefaultAddress = isDefaultAddress;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
    }

}
