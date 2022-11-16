package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.cart.dtos.CartDTO;
import com.kivanc.steamserver.cart.requests.CartRequest;
import com.kivanc.steamserver.product.dtos.ProductDTO;

public interface CartService {
    CartDTO getCardById(long id);
    void addCart(CartRequest cartRequest);
    void completePurchase(long cartId);
    void emptyTheCart(long cartId);
    void addProductToCart(long cartId, long productId);
    ProductDTO removeProductFromCart(long cartId, long productId);
}
