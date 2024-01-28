package com.practice.ordersystem.domain.Member.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MemberListResDto {
    private String name;
    private String role;
    private LocalDateTime createdTime;
}
