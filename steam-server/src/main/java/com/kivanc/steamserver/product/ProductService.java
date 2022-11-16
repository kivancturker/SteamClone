package com.kivanc.steamserver.product;

import com.kivanc.steamserver.product.requests.AddProductRequest;
import com.kivanc.steamserver.product.dtos.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO getProductById(long id);

    List<ProductDTO> getProductsByPublisherId(Long id);
    void addProduct(AddProductRequest productRequest);
    boolean checkIfAllProductsExist(List<Long> productsIds);
    boolean checkIfProductExist(long productId);
    List<Product> getMultipleProductByIds(List<Long> ids);
}
