package com.practice.ordersystem.domain.Ordering.DTO;

import com.practice.ordersystem.domain.Item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderCreateReqDto {
    private String status;
    private Long memberId;
    private List<Long> itemId;
    private List<Integer> count;
}
