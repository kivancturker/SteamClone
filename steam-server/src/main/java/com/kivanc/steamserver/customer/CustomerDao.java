package com.kivanc.steamserver.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {
    Customer getCustomerByEmail(String email);
    Customer getCustomerByUsername(String username);
    Boolean existsCustomerById(Long id);
}
