package com.xworkz.orderprocessing.controller;

import com.xworkz.orderprocessing.dto.OrderRequestDto;
import com.xworkz.orderprocessing.dto.OrderResponseDto;
import com.xworkz.orderprocessing.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderController {


    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto dto) {
        log.info("API Call: Create Order");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable String id) {
        log.info("API Call: Get Order");
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        log.info("API Call: Get All Orders");
        return ResponseEntity.ok(service.getAllOrders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable String id,
            @Valid @RequestBody OrderRequestDto dto) {
        log.info("API Call: Update Order");
        return ResponseEntity.ok(service.updateOrder(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        log.info("API Call: Delete Order");
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/bulk")
    public ResponseEntity<List<OrderResponseDto>> createBulkOrders(@Valid @RequestBody List<OrderRequestDto> orders) {
        log.info("API Call: Bulk Create Orders");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBulkOrders(orders));
    }

    @PutMapping("/bulk")
    public ResponseEntity<List<OrderResponseDto>> updateBulkOrders(@Valid @RequestBody List<OrderRequestDto> orders) {
        log.info("API Call: Bulk Update Orders");
        return ResponseEntity.ok(service.updateBulkOrders(orders));
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteBulkOrders(@RequestBody List<String> ids) {
        log.info("API Call: Bulk Delete Orders");
        service.deleteBulkOrders(ids);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bulk")
    public ResponseEntity<List<OrderResponseDto>> getBulkOrders(@RequestParam List<String> ids) {
        log.info("API Call: Bulk Get Orders");
        return ResponseEntity.ok(service.getBulkOrders(ids));
    }

}
