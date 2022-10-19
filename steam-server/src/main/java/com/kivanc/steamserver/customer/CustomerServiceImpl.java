package com.kivanc.steamserver.customer;

import com.kivanc.steamserver.customer.dtos.CustomerDTO;
import com.kivanc.steamserver.customer.exceptions.CustomerAlreadyExistException;
import com.kivanc.steamserver.customer.exceptions.CustomerDoesNotExistException;
import com.kivanc.steamserver.customer.requests.CustomerRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements  CustomerService {

    private final CustomerDao customerDao;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerDao customerDao, ModelMapper modelMapper) {
        this.customerDao = customerDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO getCustomerById(long id) {
        Optional<Customer> maybeCustomer = customerDao.findById(id);
        if (maybeCustomer.isEmpty())
        {
            throw new CustomerDoesNotExistException("Customer with Id: " + id + " does not exist");
        }
        CustomerDTO customerDTO = modelMapper.map(maybeCustomer.get(), CustomerDTO.class);
        return customerDTO;
    }
    @Override
    public void addCustomer(CustomerRequest customerRequest) {
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        // email must be unique
        isEmailExist(customer.getEmail());
        // username must be unique
        isUsernameExist(customer.getUsername());

        customerDao.save(customer);
        log.info("Customer with " + customer.getUsername() + " username added");
    }

    private void isEmailExist(String email) {
        Customer customer = customerDao.getCustomerByEmail(email);
        if (Objects.nonNull(customer))
        {
            log.warn("Email: " + email + " already exist");
            throw new CustomerAlreadyExistException("Email " + email + " already exist");
        }
    }

    private void isUsernameExist(String username) {
        Customer customer = customerDao.getCustomerByUsername(username);
        if (Objects.nonNull(customer))
        {
            log.warn("Username: " + username + " already exist");
            throw new CustomerAlreadyExistException("Username " + username + " already exist");
        }
    }
}
