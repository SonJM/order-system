package com.practice.ordersystem.domain.Member.DTO;

import com.practice.ordersystem.domain.Ordering.Ordering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MemberDetailResDto {
    private String name;
    private String email;
    private String address;
    private String role;
    private LocalDateTime createdTime;
    private List<Ordering> orderingList;
}
