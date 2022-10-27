package com.kivanc.steamserver.product;

import com.kivanc.steamserver.product.requests.AddProductRequest;
import com.kivanc.steamserver.publisher.dtos.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO getProduct(long id);

    List<ProductDTO> getProductsByUsername(String username);
    void addProduct(AddProductRequest productRequest);
}
