package com.kivanc.steamserver.productincart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInCartDao extends JpaRepository<ProductInCart, Long> {
    Boolean existsProductInCartByProductIdAndCartId(long productId, long cartId);
    Boolean existsProductInCartByCartId(long cartId);
}
