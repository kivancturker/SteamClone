package com.kivanc.steamserver.customer.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    @Size(min = 3, max = 20)
    @NotNull
    private String username;
    @Email
    @NotNull
    private String email;
    @Size(min = 6)
    @NotNull
    private String password;
}
