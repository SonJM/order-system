package com.practice.ordersystem.domain.Ordering.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemListReqDto {
    private Long itemId;
    private int count;
}
