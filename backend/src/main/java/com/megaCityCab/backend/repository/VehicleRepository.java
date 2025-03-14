package com.megaCityCab.backend.repository;

import com.megaCityCab.backend.entity.Vehicle;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    @Aggregation("{ $group: { _id: '$vehicleType' } }")
    List<String> findDistinctVehicleType();

    @Query("{ 'bookings': { $size: 0 } }")
    List<Vehicle> findAllAvailableVehicles();

    @Query("{ 'vehicleType': { $regex: ?0, $options: 'i' }, 'vehicleId': { $nin: ?1 } }")
    List<Vehicle> findByVehicleTypeLikeAndCabIdNotIn(String vehicleType, List<String> bookedVehicleIds);
}
