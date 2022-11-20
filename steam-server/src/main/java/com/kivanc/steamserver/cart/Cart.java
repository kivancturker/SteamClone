package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.productincart.ProductInCart;
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
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(mappedBy = "cart")
    private Customer customer;
    @OneToMany(mappedBy = "cart")
    private List<ProductInCart> productInCarts;
    private LocalDateTime lastModified;
    private BigDecimal price;
}
