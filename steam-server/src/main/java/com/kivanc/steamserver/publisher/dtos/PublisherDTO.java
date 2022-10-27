package com.kivanc.steamserver.publisher.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {
    long id;
    @Size(min = 3, max = 20)
    private String username;
    @Email
    private String email;
    @Min(6)
    private String password;
}
