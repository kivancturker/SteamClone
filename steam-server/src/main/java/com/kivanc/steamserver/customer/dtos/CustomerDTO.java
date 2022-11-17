package com.kivanc.steamserver.customer.dtos;

import com.kivanc.steamserver.order.Order;
import com.kivanc.steamserver.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private long id;
    @Size(min = 3, max = 20)
    private String username;
    @Email
    private String email;
    private long cartId;
    private LocalDateTime birthDate;
    private LocalDateTime createDate;
    private LocalDateTime lastLogin;
}
