package com.sidenow.freshgreenish.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"), // 상품 등록, 수정, 삭제, 문의 답글, 상품 상태변경(배송중/완료 등..) => 포함 모두
    GUEST("ROLE_GUEST", "손님"), // 메인페이지, 상세페이지, 상품 조회, 검색, 리뷰 조회(작성X), 문의글 조회(비밀글 아닌) => 이것만
    USER("ROLE_USER", "일반 사용자"); // 관리자 제외하고 모두

    private final String key;
    private final String title;
}

