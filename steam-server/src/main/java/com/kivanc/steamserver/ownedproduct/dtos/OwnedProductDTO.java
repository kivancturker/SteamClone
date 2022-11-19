package com.kivanc.steamserver.ownedproduct.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnedProductDTO {
    private long customerId;
    private long productId;
    private LocalDateTime lastTimePlayed;
    private LocalDateTime purchaseDate;
    private BigDecimal totalHours;
    private BigDecimal paidAmount;
}
