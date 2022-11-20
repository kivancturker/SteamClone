package com.kivanc.steamserver.customer;

import com.kivanc.steamserver.cart.Cart;
import com.kivanc.steamserver.cart.CartService;
import com.kivanc.steamserver.cart.dtos.CartDTO;
import com.kivanc.steamserver.cart.requests.CartRequest;
import com.kivanc.steamserver.ownedproduct.OwnedProduct;
import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import com.kivanc.steamserver.customer.dtos.CustomerDTO;
import com.kivanc.steamserver.customer.exceptions.CustomerAlreadyExistException;
import com.kivanc.steamserver.customer.exceptions.CustomerDoesNotExistException;
import com.kivanc.steamserver.customer.requests.CustomerRequest;
import com.kivanc.steamserver.customer.requests.ProductAdditionRequest;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: addProductToCustomer doesnt work
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

    // Not Finished !!!
    // Also not working
    @Override
    public void addProductToCustomer(ProductAdditionRequest productAdditionRequest) {
        long customerId = productAdditionRequest.getCustomerId();
        List<Long> productIds = productAdditionRequest.getProductIds();
        if (!productService.checkIfAllProductsExist(productIds))
        {
            throw new RecordNotFoundException("Product with ids does not exist: " + productIds);
        }
        Optional<Customer> maybeCustomer = customerDao.findById(customerId);
        if (maybeCustomer.isEmpty())
        {
            throw new RecordNotFoundException("Customer not found with id of: " + customerId);
        }
        Customer customer = maybeCustomer.get();

        List<Product> products = productService.getMultipleProductByIds(productIds);

        Customer customerAfterPurchase = giveCustomerPurchasedProducts(products, customer);

        customerDao.saveAndFlush(customerAfterPurchase);

        log.info("Products added to the customer with id: " + customer.getId());
    }

    private Customer giveCustomerPurchasedProducts(List<Product> products, Customer customer) {
        Optional<Customer> maybeCustomerAfterPurchase = customerDao.findById(customer.getId());
        Customer customerAfterPurchase = maybeCustomerAfterPurchase.get();
        List<OwnedProduct> purchasedOwnedProducts = convertProductsToOwnedProducts(products, customer);
        List<OwnedProduct> alreadyOwnedProducts = customer.getOwnedProducts();
        List<OwnedProduct> newOwnedProducts = Stream.of(purchasedOwnedProducts, alreadyOwnedProducts)
                .flatMap(ownedProducts -> ownedProducts.stream())
                .collect(Collectors.toList());
        customerAfterPurchase.setOwnedProducts(newOwnedProducts);
        return customerAfterPurchase;
    }

    private List<OwnedProduct> convertProductsToOwnedProducts(List<Product> products, Customer customer) {
        LocalDateTime purchaseDate = LocalDateTime.now();
        List<OwnedProduct> convertedOwnedProduct = products.stream()
                .map((p) -> {
                    return OwnedProduct.builder()
                            .product(p)
                            .customer(customer)
                            .totalHours(BigDecimal.ZERO)
                            .purchaseDate(purchaseDate)
                            .paidAmount(p.getPrice())
                            .build();
                })
                .collect(Collectors.toList());
        return convertedOwnedProduct;
    }

    @Override
    public void addCustomer(CustomerRequest customerRequest) {
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        // email must be unique
        isEmailExist(customer.getEmail());
        // username must be unique
        isUsernameExist(customer.getUsername());
        customer.setJoinDate(LocalDateTime.now());
        Cart cart = Cart.builder()
                .productInCarts(new ArrayList<>())
                .lastModified(LocalDateTime.now())
                .price(BigDecimal.ZERO)
                .build();
        customer.setCart(cart);
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
