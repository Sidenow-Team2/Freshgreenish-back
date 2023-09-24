package com.sidenow.freshgreenish.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class EditProduct {
    private String title; //이름
    private String subTitle; //한줄소개
    private Integer price; //가격
    private Integer discountRate; //할인율
    private String detail; //상품 설명

    private String deliveryType; //배송방법
    private String seller; //판매자
    private String packageType; //포장타입
    private String unit; //판매단위
    private String capacity; //중량/용량
    private String origin; //원산지
    private String notification; //안내사항

    private String storageMethod; //상품상태(냉장,실온,냉동)
    private String brand; //제조사
    private String weight; //상품무게
    private String variety; //품종
    private String harvestSeason; //수확시기

    private Boolean recommendation; //추천상품
    private Boolean subscription; //구독가능여부

    private List<Long> deleteImageId; //삭제할이미지리스트
}
