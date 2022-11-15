package com.kivanc.steamserver.customer;

import com.kivanc.steamserver.cart.Cart;
import com.kivanc.steamserver.cart.CartService;
import com.kivanc.steamserver.cart.requests.CartRequest;
import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import com.kivanc.steamserver.customer.dtos.CustomerDTO;
import com.kivanc.steamserver.customer.exceptions.CustomerAlreadyExistException;
import com.kivanc.steamserver.customer.exceptions.CustomerDoesNotExistException;
import com.kivanc.steamserver.customer.requests.CustomerRequest;
import com.kivanc.steamserver.customer.requests.ProductAdditionRequest;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CustomerServiceImpl implements  CustomerService {

    private final CustomerDao customerDao;
    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao, ModelMapper modelMapper, ProductService productService, CartService cartService) {
        this.customerDao = customerDao;
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.cartService = cartService;
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
    public void addProductToCustomer(ProductAdditionRequest productAdditionRequest) {
        long customerId = productAdditionRequest.getCustomerId();
        List<Long> productIds = productAdditionRequest.getProductIds();
        if (!productService.checkIfAllProductsExist(productIds))
        {
            throw new RecordNotFoundException("Product with ids does not exist: " + productIds);
        }
        if (!customerDao.existsCustomerById(customerId))
        {
            throw new RecordNotFoundException("Customer not found with id of: " + customerId);
        }
        Optional<Customer> maybeCustomer = customerDao.findById(customerId);
        Customer customer = maybeCustomer.get();
        List<Product> products = productService.getMultipleProductByIds(productIds);
        List<Product> customersOverallProducts = Stream.of(products, customer.getProducts())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        customer.setProducts(customersOverallProducts);
        customerDao.save(customer);
    }

    @Override
    public void addCustomer(CustomerRequest customerRequest) {
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        // email must be unique
        isEmailExist(customer.getEmail());
        // username must be unique
        isUsernameExist(customer.getUsername());
        customerDao.saveAndFlush(customer);
        log.info("Customer with " + customer.getUsername() + " username added");
        // Every customer has a cart
        CartRequest cartRequest = new CartRequest(customer.getId());
        cartService.addCart(cartRequest);
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
