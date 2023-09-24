package com.sidenow.freshgreenish.domain.address.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sidenow.freshgreenish.domain.user.entity.User;
import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
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



}
