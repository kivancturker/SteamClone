package com.kivanc.steamserver.customer;

import com.kivanc.steamserver.cart.Cart;
import com.kivanc.steamserver.order.Order;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    private LocalDateTime birthDate;
    private LocalDateTime createDate;
    private LocalDateTime lastLogin;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
    @OneToMany
    private List<Product> products;
    @OneToOne
    @JoinColumn(name="cart_id", referencedColumnName="id")
    private Cart cart;
}
