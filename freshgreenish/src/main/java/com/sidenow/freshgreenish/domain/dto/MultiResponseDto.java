package com.sidenow.freshgreenish.domain.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiResponseDto<T> {
    private List<T> data;
    private PageInfo pageInfo;

    @Builder
    public MultiResponseDto(Page<T> data) {
        this.data = data.getContent();
        this.pageInfo = PageInfo.builder()
                .page(data.getNumber() + 1)
                .size(data.getSize())
                .totalElements(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .build();
    }
}
