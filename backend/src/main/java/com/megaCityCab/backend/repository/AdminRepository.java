package com.megaCityCab.backend.repository;

import com.megaCityCab.backend.entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {

    boolean existsByUsername(String username);
    Optional<Admin> findByUsername(String username);
}
