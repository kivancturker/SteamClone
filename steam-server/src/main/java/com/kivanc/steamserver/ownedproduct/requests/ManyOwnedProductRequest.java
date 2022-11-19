package com.kivanc.steamserver.ownedproduct.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManyOwnedProductRequest {
    private long customerId;
    private List<ProductWithPrice> productWithPrices;
}
