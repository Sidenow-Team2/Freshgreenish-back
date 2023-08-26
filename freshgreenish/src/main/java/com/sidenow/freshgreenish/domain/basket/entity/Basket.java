package com.sidenow.freshgreenish.domain.basket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sidenow.freshgreenish.domain.user.entity.User;
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
@SQLDelete(sql = "UPDATE basket SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Basket extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Basket(User user) {
        this.user = user;
    }
}
