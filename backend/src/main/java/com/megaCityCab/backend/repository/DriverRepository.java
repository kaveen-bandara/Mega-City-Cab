package com.megaCityCab.backend.repository;

import com.megaCityCab.backend.entity.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {

    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Driver> findByPhoneNumber(String phoneNumber);
}
