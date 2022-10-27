package com.kivanc.steamserver.publisher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherDao extends JpaRepository<Publisher, Long> {
    Boolean existsPublisherByUsername(String username);
    Boolean existsPublisherByEmail(String email);
    Optional<Publisher> getPublisherByUsername(String username);
}
