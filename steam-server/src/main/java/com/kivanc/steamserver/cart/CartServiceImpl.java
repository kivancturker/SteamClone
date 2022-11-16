package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.cart.dtos.CartDTO;
import com.kivanc.steamserver.cart.requests.CartRequest;
import com.kivanc.steamserver.core.exceptions.RecordAlreadyExistException;
import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.customer.CustomerService;
import com.kivanc.steamserver.customer.dtos.CustomerDTO;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.product.ProductService;
import com.kivanc.steamserver.product.dtos.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public CartDTO getCardById(long id) {
        Optional<Cart> maybeCart = cartDao.findById(id);
        if (maybeCart.isEmpty())
        {
            throw new RecordNotFoundException("Cart with id: " + id + " Not Found");
        }
        CartDTO cartDTO = modelMapper.map(maybeCart.get(), CartDTO.class);
        List<Long> productIds = maybeCart.get().getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        cartDTO.setProductIds(productIds);
        return cartDTO;
    }

    @Override
    public void addCart(CartRequest cartRequest) {
        CustomerDTO customerDTO = customerService.getCustomerById(cartRequest.getCustomerId());
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Cart cart = Cart.builder()
                .customer(customer)
                .products(new ArrayList<>())
                .lastModified(LocalDateTime.now())
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
        throwExceptionIfProductExist(productId);
        Cart cart = getCartWithId(cartId);

        ProductDTO productDTO = productService.getProductById(productId);
        Product product = modelMapper.map(productDTO, Product.class);
        List<Product> productsInCart = cart.getProducts();
        boolean isCartContainsProduct = productsInCart.stream()
                .anyMatch(p -> p.getId() == product.getId());
        if (isCartContainsProduct) {
            throw new RecordAlreadyExistException("Product with id: " +
                    productId + " is in the Cart with id: " + cartId);
        }
        updateCartAfterProductAdded(cart, product);

        cartDao.save(cart);
    }

    private void throwExceptionIfProductExist(long productId) {
        if (!productService.checkIfProductExist(productId)) {
            throw new RecordNotFoundException("Product with id: " + productId + " Not Found");
        }
    }
    private Cart getCartWithId(long cartId) {
        Optional<Cart> maybeCart = cartDao.findById(cartId);
        if (maybeCart.isEmpty()) {
            throw new RecordNotFoundException("Cart with id: " + cartId + " Not Found");
        }
        return maybeCart.get();
    }

    private void updateCartAfterProductAdded(Cart cart, Product product) {
        List<Product> productsInCart = cart.getProducts();
        productsInCart.add(product);
        cart.setProducts(productsInCart);

        BigDecimal prevPrice = cart.getPrice();
        cart.setPrice(prevPrice.add(product.getPrice()));

        cart.setLastModified(LocalDateTime.now());
    }

    @Override
    public ProductDTO removeProductFromCart(long cartId, long productId) {
        // Check if product is on the cart
        throwExceptionIfProductExist(productId);
        // Get Cart
        Cart cart = getCartWithId(cartId);
        // Remove product from the cart
        List<Product> productsInCart = cart.getProducts();
        List<Product> productsAfterRemoval = productsInCart.stream()
                .filter(product -> product.getId() != productId)
                .collect(Collectors.toList());
        cart.setProducts(productsAfterRemoval);
        // Decrease the cart price
        Optional<Product> maybeProduct = productsInCart.stream().findAny();
        Product product = maybeProduct.get();
        cart.setPrice(cart.getPrice().subtract(product.getPrice()));
        // Change lastModified
        cart.setLastModified(LocalDateTime.now());
        // Save the database
        cartDao.save(cart);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }
}
