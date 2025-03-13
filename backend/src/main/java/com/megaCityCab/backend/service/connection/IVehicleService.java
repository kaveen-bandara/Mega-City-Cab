package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IVehicleService {

    Response addNewVehicle(MultipartFile photo, String licensePlate, String vehicleType, String model, String color, String description, BigDecimal fare, String driverName);

    List<String> getAllVehicleTypes();

    Response getAllVehicles();

    Response getVehicleById(String vehicleId);

    Response deleteVehicle(String vehicleId);

    Response updateVehicleDetails(String vehicleId, String licensePlate, String vehicleType, String model, String color, String description, BigDecimal fare, String driverName, MultipartFile photo);

    Response getAllAvailableVehicles();

    Response getAvailableVehiclesByDateAndVehicleType(LocalDate startDate, LocalDate endDate, String vehicleType);
}
