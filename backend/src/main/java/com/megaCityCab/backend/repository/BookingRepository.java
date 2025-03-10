package com.megaCityCab.backend.repository;

import com.megaCityCab.backend.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, String> {

    Optional<Booking> findByConfirmationCode(String confirmationCode);

    @Query("{ 'bookings.pickupDateTime': { $not: { $gte: ?0, $lte: ?1 } }, 'vehicleType': ?2 }")
    List<Booking> findBookingsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
}
