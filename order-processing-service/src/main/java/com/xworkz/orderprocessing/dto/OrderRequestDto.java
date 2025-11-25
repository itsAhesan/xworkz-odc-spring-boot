package com.xworkz.orderprocessing.dto;

import com.xworkz.orderprocessing.validation.AlphaSpace;
import com.xworkz.orderprocessing.validation.ValidStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDto {

  private String id;

  @NotBlank(message = "Customer name should not be empty")
  @AlphaSpace
  private String customerName;

  @NotNull(message = "Amount cannot be null")
  @Min(value = 1, message = "Amount should be more than 0")
  private Double amount;

  @NotBlank(message = "Status cannot be blank")
  @ValidStatus
  private String status;
}
