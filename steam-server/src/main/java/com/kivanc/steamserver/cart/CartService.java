package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.cart.dtos.CartDTO;
import com.kivanc.steamserver.cart.requests.CartRequest;
import com.kivanc.steamserver.product.dtos.ProductDTO;
import com.kivanc.steamserver.productincart.dtos.ProductInCartDTO;

public interface CartService {
    CartDTO getCardById(long id);
    void addCart(CartRequest cartRequest);
    void emptyTheCart(long cartId);
    void addProductToCart(long cartId, long productId);
    ProductInCartDTO removeProductFromCart(long productInCart);
}
