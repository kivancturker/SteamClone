package com.kivanc.steamserver.order.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private long orderId;
    private long customerId;
    private List<Long> productIds;
    private LocalDateTime orderDate;
    private BigDecimal price;
}
