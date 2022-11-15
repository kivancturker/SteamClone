package com.kivanc.steamserver.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kivanc.steamserver.cart.Cart;
import com.kivanc.steamserver.core.constants.LocalDateConstants;
import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.order.Order;
import com.kivanc.steamserver.publisher.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    private LocalDate releaseDate;
    @NotNull
    private BigDecimal price;
    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "id"))
    private List<String> mediaUrls;
    @ManyToOne
    private Publisher publisher;
    @OneToMany(mappedBy = "product")
    private List<Order> orders;
    @OneToMany
    private List<Cart> carts;
    @OneToMany
    private List<Customer> customers;
}
