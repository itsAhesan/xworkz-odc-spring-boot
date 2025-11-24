package com.xworkz.orderprocessing.repository;


import com.xworkz.orderprocessing.entity.OrderEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {

    boolean existsByCustomerNameAndAmount(String customerName, Double amount);
}
