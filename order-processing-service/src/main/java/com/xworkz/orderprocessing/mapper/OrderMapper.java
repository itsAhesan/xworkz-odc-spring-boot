package com.xworkz.orderprocessing.mapper;

import com.xworkz.orderprocessing.dto.OrderRequestDto;
import com.xworkz.orderprocessing.dto.OrderResponseDto;
import com.xworkz.orderprocessing.entity.OrderEntity;

import java.time.LocalDateTime;

public class OrderMapper {

    public static OrderEntity toEntity(OrderRequestDto dto) {
        return OrderEntity.builder()
                .customerName(dto.getCustomerName())
                .amount(dto.getAmount())
                .status(dto.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static OrderResponseDto toResponse(OrderEntity entity) {
        return OrderResponseDto.builder()
                .id(entity.getId())
                .customerName(entity.getCustomerName())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
