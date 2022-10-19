package com.kivanc.steamserver.customer;

import com.kivanc.steamserver.customer.dtos.CustomerDTO;
import com.kivanc.steamserver.customer.requests.CustomerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

public interface CustomerService {
    void addCustomer(CustomerRequest customerRequest);

    CustomerDTO getCustomerById(long id);
}
