package com.megaCityCab.backend.repository;

import com.megaCityCab.backend.entity.Vehicle;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    @Aggregation("{ $group: { _id: '$vehicleType' } }")
    List<VehicleTypeProjection> findDistinctVehicleType();

    interface VehicleTypeProjection {
        String getId();
    }

    @Query("{ 'bookings': { $size: 0 } }")
    List<Vehicle> findAllAvailableVehicles();

    List<Vehicle> findByVehicleTypeAndCabIdNotIn(Vehicle.VehicleType vehicleType, List<String> bookedVehicleIds);
}
