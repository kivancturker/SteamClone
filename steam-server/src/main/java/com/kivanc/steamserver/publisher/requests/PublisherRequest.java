package com.kivanc.steamserver.publisher.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublisherRequest {
    private String username;
    private String email;
    private String password;
}
