package com.megaCityCab.backend.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicles")
public class Vehicle {

    @Id
    private String cabId;

    @NotBlank(message = "License plate is required!")
    @Indexed(unique = true)
    private String licensePlate;

    public enum VehicleType {
        SEDAN, SUV, MINIVAN, HATCHBACK, LUXURY, ELECTRIC;
    }

    @NotBlank(message = "Vehicle type is required!")
    private VehicleType vehicleType;

    @NotBlank(message = "Model is required!")
    private String model;

    @NotBlank(message = "Color is required!")
    private String color;

    @NotBlank(message = "Photo URL is required!")
    private String cabPhotoUrl;

    @NotBlank(message = "Description is required!")
    private String description;

    @Positive(message = "Cab fare must be a positive value!")
    @NotNull(message = "Cab fare is required!")
    private BigDecimal cabFare;

    private Boolean inWork = Boolean.FALSE;

    @Indexed
    private String driverId;

    private List<String> bookingIds = new ArrayList<>();
}
