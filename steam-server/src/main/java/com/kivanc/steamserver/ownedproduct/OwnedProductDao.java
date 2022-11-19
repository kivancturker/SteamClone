package com.kivanc.steamserver.ownedproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnedProductDao extends JpaRepository<OwnedProduct, Long> {
    boolean existsOwnedProductByCustomerIdAndProductId(long customerId, long productId);
}
