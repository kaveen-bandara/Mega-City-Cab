package com.megaCityCab.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.megaCityCab.backend.entity.Vehicle;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDTO {

    private String cabId;
    private String licensePlate;
    private Vehicle.VehicleType vehicleType;
    private String model;
    private String color;
    private String cabPhotoUrl;
    private String description;
    private BigDecimal cabFare;
    private String driverName;

    private List<BookingDTO> bookings;
}
