package com.kivanc.steamserver.productincart;

import com.kivanc.steamserver.cart.Cart;
import com.kivanc.steamserver.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_in_carts")
public class ProductInCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Product product;
}
