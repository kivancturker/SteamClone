package com.kivanc.steamserver.order;

import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Customer customer;
    @OneToMany
    private List<Product> products;
    LocalDateTime orderDate;
    BigDecimal price;
}
