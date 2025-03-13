package com.megaCityCab.backend.repository;

import com.megaCityCab.backend.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, String> {

    Optional<Booking> findByConfirmationCode(String confirmationCode);

    @Query("{ 'startDate': { $lte: ?1 }, 'endDate': { $gte: ?0 } }")
    List<Booking> findBookingsByDateRange(LocalDate startDate, LocalDate endDate);
}
