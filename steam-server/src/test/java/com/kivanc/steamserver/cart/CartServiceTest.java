package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.core.exceptions.RecordAlreadyExistException;
import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import com.kivanc.steamserver.customer.CustomerService;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.product.ProductService;
import com.kivanc.steamserver.publisher.dtos.ProductDTO;
import org.assertj.core.api.ThrowableAssert;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

// TODO: addProductToCart method complete the last case (complete coverage)

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    CartDao cartDao;
    @Mock
    ModelMapper modelMapper;
    @Mock
    ProductService productService;
    @Mock
    CustomerService customerService;

    CartService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CartServiceImpl(modelMapper, cartDao, customerService, productService);
    }

    @Test
    void AddProductToCart_When_ProductIdIsNotValid_Then_ThrowRecordNotFoundException() {
        // given
        long cartId = 3;
        long productId = 6;
        given(productService.checkIfProductExist(productId)).willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> { underTest.addProductToCart(cartId, productId);})
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("Product with id: " + productId + " Not Found");

        verify(cartDao, never()).save(any());
    }

    @Test
    void AddProductToCart_When_CartDoesNotExist_Then_ThrowRecordNotFoundException() {
        // given
        long cartId = 3;
        long productId = 6;
        Optional<Cart> maybeCart = Optional.empty();
        given(cartDao.findById(cartId)).willReturn(maybeCart);
        given(productService.checkIfProductExist(productId)).willReturn(true);
        // when
        // then
        assertThatThrownBy(() -> { underTest.addProductToCart(cartId, productId);})
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("Cart with id: " + cartId + " Not Found");

        verify(cartDao, never()).save(any());
    }

    @Test
    void AddProductToCart_When_ProductAlreadyInCart_Then_ThrowRecordAlreadyExistException() {
        // given
        long cartId = 3;
        long productId = 6;
        List<Product> productsInCart = new ArrayList<>();
        Product product = Product.builder()
                .id(1)
                .name("product1")
                .price(BigDecimal.valueOf(21))
                .build();
        productsInCart.add(product);
        Cart cart = Cart.builder().products(productsInCart).build();
        Optional<Cart> maybeCart = Optional.of(cart);
        given(cartDao.findById(cartId)).willReturn(maybeCart);
        given(modelMapper.map(any(), any())).willReturn(product);
        given(productService.checkIfProductExist(productId)).willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> { underTest.addProductToCart(cartId, productId);})
                .isInstanceOf(RecordAlreadyExistException.class)
                .hasMessage("Product with id: " + productId + " is in the Cart with id: " + cartId);

        verify(cartDao, never()).save(any());
    }
}