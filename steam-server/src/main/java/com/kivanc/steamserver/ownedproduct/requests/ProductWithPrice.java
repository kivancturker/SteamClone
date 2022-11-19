package com.kivanc.steamserver.ownedproduct.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithPrice {
    private long productId;
    private BigDecimal paidAmount;
}
