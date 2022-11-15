package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Customer customer;
    @OneToMany
    private List<Product> products;
    private LocalDate lastModified;
    private BigDecimal price;
}
