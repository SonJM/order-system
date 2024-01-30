package com.practice.ordersystem.domain.Ordering.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateReqDto {
    private Long memberId;
    private List<OrderItemListReqDto> items;
}
