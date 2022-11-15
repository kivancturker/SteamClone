package com.kivanc.steamserver.product;

import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    Optional<List<Product>> getProductsByPublisherId(Long id);
    Boolean existsProductsByIdIn(List<Long> ids);
    Boolean existsProductById(long id);
    Optional<List<Product>> getProductsByIdIn(List<Long> ids);
}
