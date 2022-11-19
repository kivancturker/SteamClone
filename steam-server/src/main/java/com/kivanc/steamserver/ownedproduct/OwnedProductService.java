package com.kivanc.steamserver.ownedproduct;

import com.kivanc.steamserver.ownedproduct.dtos.OwnedProductDTO;
import com.kivanc.steamserver.ownedproduct.requests.ManyOwnedProductRequest;
import com.kivanc.steamserver.ownedproduct.requests.OwnedProductRequest;

public interface OwnedProductService {
    public OwnedProductDTO addOwnedProduct(OwnedProductRequest ownedProductRequest);
    public void addManyOwnedProducts(ManyOwnedProductRequest manyOwnedProductRequest);
}
