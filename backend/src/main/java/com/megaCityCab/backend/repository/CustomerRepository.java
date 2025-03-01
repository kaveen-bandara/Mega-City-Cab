package com.megaCityCab.backend.repository;

import com.megaCityCab.backend.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email);
}
