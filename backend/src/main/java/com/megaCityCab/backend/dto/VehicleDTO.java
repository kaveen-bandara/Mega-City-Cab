package com.megaCityCab.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.megaCityCab.backend.entity.Vehicle;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDTO {

    private String id;
    private String licensePlate;
    private String vehicleType;
    private String model;
    private String color;
    private String vehiclePhotoUrl;
    private String description;
    private BigDecimal fare;
    private String driverName;

    private List<BookingDTO> bookings;
}
