package com.xworkz.orderprocessing.service;

import com.xworkz.orderprocessing.dto.OrderRequestDto;
import com.xworkz.orderprocessing.dto.OrderResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto dto);

    OrderResponseDto getOrderById(String id);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto updateOrder(String id, OrderRequestDto dto);

    void deleteOrder(String id);

    List<OrderResponseDto> createBulkOrders(@Valid List<OrderRequestDto> orders);

    List<OrderResponseDto> updateBulkOrders(@Valid List<OrderRequestDto> orders);

    void deleteBulkOrders(List<String> ids);

    List<OrderResponseDto> getBulkOrders(List<String> ids);

    Page<OrderResponseDto> getOrdersWithPagination(int page, int size, String sortBy, String direction);
}
