package com.kivanc.steamserver.product.requests;

import com.kivanc.steamserver.publisher.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    private String name;
    private LocalDate releaseDate;
    private BigDecimal price;
    private long publisherId;
}
