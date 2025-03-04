package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IVehicleService {

    Response addNewVehicle(MultipartFile photo, String licensePlate, String vehicleType, String model, String color, String description, BigDecimal cabFare, String driverName);

    List<String> getAllVehicleTypes();

    Response getAllVehicles();

    Response getVehicleById(String cabId);

    Response deleteVehicle(String cabId);

    Response updateVehicleDetails(String cabId, String licensePlate, String vehicleType, String model, String color, String description, BigDecimal cabFare, String driverName, MultipartFile photo);

    Response getAllAvailableVehicles();

    Response getAvailableVehiclesByDateTimeAndVehicleType(LocalDateTime pickupDateTime, String vehicleType);
}
