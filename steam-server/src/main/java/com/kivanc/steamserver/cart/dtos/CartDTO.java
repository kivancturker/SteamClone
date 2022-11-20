package com.kivanc.steamserver.cart.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private long id;
    private long customerId;
    private List<Long> productInCartIds;
    private LocalDateTime lastModified;
    private BigDecimal price;
}
