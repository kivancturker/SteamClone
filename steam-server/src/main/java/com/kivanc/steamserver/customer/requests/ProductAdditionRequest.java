package com.kivanc.steamserver.customer.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdditionRequest {
    private List<Long> productIds;
    private long customerId;
}
