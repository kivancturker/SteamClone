package com.kivanc.steamserver.productincart.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInCartDTO {
    private long id;
    private long productId;
    private long cartId;
}
