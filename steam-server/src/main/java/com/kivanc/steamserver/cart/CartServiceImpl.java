package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.cart.requests.CartRequest;
import com.kivanc.steamserver.core.exceptions.RecordAlreadyExistException;
import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.customer.CustomerService;
import com.kivanc.steamserver.customer.dtos.CustomerDTO;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.product.ProductService;
import com.kivanc.steamserver.publisher.dtos.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: Do purchase action at Cart Service.
// TODO: Test all the methods later.

@Service
public class CartServiceImpl implements CartService {

    private final ModelMapper modelMapper;
    private final CartDao cartDao;
    private CustomerService customerService;
    private ProductService productService;

    @Autowired
    public CartServiceImpl(ModelMapper modelMapper,
                           CartDao cartDao,
                           @Lazy CustomerService customerService,
                           ProductService productService) {
        this.modelMapper = modelMapper;
        this.cartDao = cartDao;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public void addCart(CartRequest cartRequest) {
        CustomerDTO customerDTO = customerService.getCustomerById(cartRequest.getCustomerId());
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Cart cart = Cart.builder()
                .customer(customer)
                .products(new ArrayList<>())
                .lastModified(LocalDate.now())
                .price(BigDecimal.ZERO)
                .build();
        cartDao.save(cart);
    }

    @Override
    public void completePurchase(long cartId) {
        // Do payment - If not enough money throw exception. (Catch it on controller)
        // Create order
        // Empty the cart
    }

    @Override
    public void emptyTheCart(long cartId) {
        // Copy the data on the previous cart
        // Delete the previous cart
        // Create a new one with previos cart information. (e.g customerId)
    }

    @Override
    public void addProductToCart(long cartId, long productId) {
        if (!productService.checkIfProductExist(productId))
        {
            throw new RecordNotFoundException("Product with id: " + productId + " Not Found");
        }

        Optional<Cart> maybeCart = cartDao.findById(cartId);
        if (maybeCart.isEmpty())
        {
            throw new RecordNotFoundException("Cart with id: " + cartId + " Not Found");
        }
        Cart cart = maybeCart.get();

        ProductDTO productDTO = productService.getProductById(productId);
        Product product = modelMapper.map(productDTO, Product.class);
        List<Product> productsInCart = cart.getProducts();
        if (productsInCart.contains(product))
        {
            throw new RecordAlreadyExistException("Product with id: " +
                    productId + " is in the Cart with id: " + cartId);
        }
        productsInCart.add(product);
        cart.setProducts(productsInCart);

        BigDecimal prevPrice = cart.getPrice();
        cart.setPrice(prevPrice.add(product.getPrice()));

        cartDao.save(cart);
    }

    @Override
    public void removeProductFromCart(long cartId, long productId) {
        // Check if product is on the cart
        // Remove product from the cart
        // Decrease the cart price
    }
}
