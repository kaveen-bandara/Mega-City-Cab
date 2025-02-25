package com.megaCityCab.backend.repository;

import com.megaCityCab.backend.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, String> {

    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);
}
