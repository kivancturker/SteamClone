package com.kivanc.steamserver.ownedproduct;

import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "owned_products")
public class OwnedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private LocalDateTime lastTimePlayed;
    private LocalDateTime purchaseDate;
    private BigDecimal totalHours;
    private BigDecimal paidAmount;
}
