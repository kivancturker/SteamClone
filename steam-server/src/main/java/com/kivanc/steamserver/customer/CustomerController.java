package com.kivanc.steamserver.customer;

import com.kivanc.steamserver.customer.dtos.CustomerDTO;
import com.kivanc.steamserver.customer.requests.CustomerRequest;
import com.kivanc.steamserver.customer.requests.ProductAdditionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "{id}")
    public  ResponseEntity<CustomerDTO> getCustomer(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addCustomer(@RequestBody CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/addProducts")
    public ResponseEntity<HttpStatus> addProductsToCustomer(@RequestBody ProductAdditionRequest productAdditionRequest) {
        customerService.addProductToCustomer(productAdditionRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
