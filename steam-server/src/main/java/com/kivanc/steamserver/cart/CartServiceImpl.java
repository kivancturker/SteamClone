package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.cart.dtos.CartDTO;
import com.kivanc.steamserver.cart.requests.CartRequest;
import com.kivanc.steamserver.core.exceptions.RecordAlreadyExistException;
import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.customer.CustomerService;
import com.kivanc.steamserver.customer.dtos.CustomerDTO;
import com.kivanc.steamserver.order.OrderService;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.product.ProductService;
import com.kivanc.steamserver.product.dtos.ProductDTO;
import com.kivanc.steamserver.productincart.ProductInCart;
import com.kivanc.steamserver.productincart.ProductInCartService;
import com.kivanc.steamserver.productincart.dtos.ProductInCartDTO;
import com.kivanc.steamserver.productincart.requests.AddProductInCartRequest;
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


@Service
public class CartServiceImpl implements CartService {

    private final ModelMapper modelMapper;
    private final CartDao cartDao;
    private ProductInCartService productInCartService;
    private ProductService productService;

    @Autowired
    public CartServiceImpl(ModelMapper modelMapper,
                           CartDao cartDao,
                           ProductInCartService productInCartService,
                           ProductService productService) {
        this.modelMapper = modelMapper;
        this.cartDao = cartDao;
        this.productInCartService = productInCartService;
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
        List<Long> productInCartIds = maybeCart.get().getProductInCarts().stream()
                .map(productInCart -> productInCart.getId())
                .collect(Collectors.toList());
        cartDTO.setProductInCartIds(productInCartIds);
        return cartDTO;
    }

    @Override
    public void addCart(CartRequest cartRequest) {
        Customer customer = Customer.builder().id(cartRequest.getCustomerId()).build();
        Cart cart = Cart.builder()
                .customer(customer)
                .productInCarts(new ArrayList<>())
                .lastModified(LocalDateTime.now())
                .price(BigDecimal.ZERO)
                .build();
        cartDao.save(cart);
    }

    @Override
    public void emptyTheCart(long cartId) {

    }

    @Override
    public void addProductToCart(long cartId, long productId) {
        Optional<Cart> maybeCart = cartDao.findById(cartId);
        if (maybeCart.isEmpty())
        {
            throw new RecordNotFoundException("Cart with id: " + cartId + " not found");
        }
        AddProductInCartRequest request = AddProductInCartRequest.builder()
                .cartId(cartId)
                .productId(productId)
                .build();
        productInCartService.addProductInCart(request);
        ProductDTO productDTO = productService.getProductById(productId);
        Product product = modelMapper.map(productDTO, Product.class);
        Cart cart = maybeCart.get();
        BigDecimal prevPrice = cart.getPrice();
        cart.setPrice(prevPrice.add(product.getPrice()));
        cartDao.save(cart);
    }


    @Override
    public ProductInCartDTO removeProductFromCart(long productInCart) {
        ProductInCartDTO deletedDTO = productInCartService.deleteProductInCart(productInCart);
        long cartId = deletedDTO.getCartId();
        long productId = deletedDTO.getProductId();
        Optional<Cart> maybeCart = cartDao.findById(cartId);
        Cart cart = maybeCart.get();
        ProductDTO productDTO = productService.getProductById(productId);
        Product product = modelMapper.map(productDTO, Product.class);
        BigDecimal prevPrice = cart.getPrice();
        cart.setPrice(prevPrice.subtract(product.getPrice()));
        cartDao.save(cart);
        return deletedDTO;
    }

}
