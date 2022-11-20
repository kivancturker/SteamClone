package com.kivanc.steamserver.productincart;

import com.kivanc.steamserver.productincart.dtos.ProductInCartDTO;
import com.kivanc.steamserver.productincart.requests.AddProductInCartRequest;
import com.kivanc.steamserver.productincart.requests.DeleteProductInCartRequest;

public interface ProductInCartService {
    ProductInCartDTO getProductInCartById(long productInCartId);
    ProductInCartDTO addProductInCart(AddProductInCartRequest request);
    ProductInCartDTO deleteProductInCart(long productInCartId);
}
