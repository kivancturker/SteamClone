package com.kivanc.steamserver.customer;

import com.kivanc.steamserver.cart.Cart;
import com.kivanc.steamserver.ownedproduct.OwnedProduct;
import com.kivanc.steamserver.order.Order;
import com.kivanc.steamserver.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
    private LocalDateTime joinDate;
    private LocalDateTime lastLogin;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
    @OneToMany(mappedBy = "customer")
    private List<OwnedProduct> ownedProducts;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    private Cart cart;
}
