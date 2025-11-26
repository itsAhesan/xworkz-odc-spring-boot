package com.xworkz.orderprocessing.service;

import com.xworkz.orderprocessing.dto.OrderRequestDto;
import com.xworkz.orderprocessing.dto.OrderResponseDto;
import com.xworkz.orderprocessing.entity.OrderEntity;
import com.xworkz.orderprocessing.exception.DuplicateResourceException;
import com.xworkz.orderprocessing.exception.ResourceNotFoundException;
import com.xworkz.orderprocessing.mapper.OrderMapper;
import com.xworkz.orderprocessing.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository repository;


    @Override
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        log.info("Creating order for customer: {}", dto.getCustomerName());

        if (repository.existsByCustomerNameAndAmount(dto.getCustomerName(), dto.getAmount())) {
            throw new DuplicateResourceException("Order already exists for this customer and amount");
        }

        OrderEntity entity = OrderMapper.toEntity(dto);
        OrderEntity saved = repository.save(entity);

        log.info("Order created successfully with id: {}", saved.getId());

        return OrderMapper.toResponse(saved);
    }

    @Override
    public OrderResponseDto getOrderById(String id) {
        log.info("Fetching order by id: {}", id);

        OrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return OrderMapper.toResponse(entity);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        log.info("Fetching all orders");

        return repository.findAll().stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto updateOrder(String id, OrderRequestDto dto) {
        log.info("Updating order with id: {}", id);

        OrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        entity.setCustomerName(dto.getCustomerName());
        entity.setAmount(dto.getAmount());
        entity.setStatus(dto.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());

        OrderEntity saved = repository.save(entity);

        log.info("Order updated successfully for id: {}", id);

        return OrderMapper.toResponse(saved);
    }

    @Override
    public void deleteOrder(String id) {
        log.warn("Deleting order with id: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found");
        }

        repository.deleteById(id);

        log.info("Order deleted with id: {}", id);
    }


    @Override
    public List<OrderResponseDto> createBulkOrders(List<OrderRequestDto> dtoList) {
        log.info("Bulk create request received: {} orders", dtoList.size());

        List<OrderEntity> entities = new ArrayList<>();

        for (OrderRequestDto dto : dtoList) {

            // Duplicate check
            if (repository.existsByCustomerNameAndAmount(dto.getCustomerName(), dto.getAmount())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Duplicate order found for customer: " + dto.getCustomerName()
                );
            }

            // Use mapper
            OrderEntity entity = OrderMapper.toEntity(dto);
            entities.add(entity);
        }

        List<OrderEntity> saved = repository.saveAll(entities);

        log.info("Bulk create successful, {} orders saved", saved.size());

        return saved.stream().map(OrderMapper::toResponse).toList();
    }
    @Override
    public List<OrderResponseDto> updateBulkOrders(List<OrderRequestDto> dtoList) {
        log.info("Bulk update request received for {} orders", dtoList.size());

        List<OrderResponseDto> updatedList = new ArrayList<>();

        for (OrderRequestDto dto : dtoList) {

            if (dto.getId() == null || dto.getId().isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "ID is required for bulk update"
                );
            }

            OrderEntity entity = repository.findById(dto.getId())
                    .orElseThrow(() ->
                            new NoSuchElementException("Order not found with id: " + dto.getId())
                    );

            // Update manually (mapper cannot handle ID updates)
            entity.setCustomerName(dto.getCustomerName());
            entity.setAmount(dto.getAmount());
            entity.setStatus(dto.getStatus());
            entity.setUpdatedAt(LocalDateTime.now());

            OrderEntity updated = repository.save(entity);

            // Use mapper for response
            updatedList.add(OrderMapper.toResponse(updated));
        }

        log.info("Bulk update completed for {} orders", updatedList.size());
        return updatedList;
    }

    @Override
    public List<OrderResponseDto> getBulkOrders(List<String> ids) {
        log.info("Bulk get request for {} IDs", ids.size());

        List<OrderEntity> found = repository.findAllById(ids);

        if (found.isEmpty()) {
            throw new NoSuchElementException("No orders found for given IDs");
        }

        return found.stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteBulkOrders(List<String> ids) {
        log.warn("Bulk delete request received for {} IDs", ids.size());

        for (String id : ids) {
            if (!repository.existsById(id)) {
                throw new NoSuchElementException("Order not found with id: " + id);
            }
        }

        repository.deleteAllById(ids);

        log.info("Bulk delete successful for {} orders", ids.size());
    }

    @Override
    public Page<OrderResponseDto> getOrdersWithPagination(int page, int size, String sortBy, String direction) {
        log.info("Fetching paginated orders page={}, size={}, sortBy={}, direction={}",
                page, size, sortBy, direction);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<OrderEntity> entityPage = repository.findAll(pageable);

        return entityPage.map(OrderMapper::toResponse);
    }


}
