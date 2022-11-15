package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.cart.requests.CartRequest;

public interface CartService {
    void addCart(CartRequest cartRequest);
    void completePurchase(long cartId);
    void emptyTheCart(long cartId);
    void addProductToCart(long cartId, long productId);
    void removeProductFromCart(long cartId, long productId);
}
