package com.kivanc.steamserver.order;

import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.publisher.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

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
    @ManyToOne
    private Product product;
    LocalDate orderDate;
    BigDecimal price;
}
