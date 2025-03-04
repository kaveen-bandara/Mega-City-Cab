package com.megaCityCab.backend.entity;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "License plate is required!")
    @Pattern(
            regexp = "^(?:([A-Z]{2}\\s?-?\\d{4})|([A-Z]{3}\\s?-?\\d{4}))$",
            message = "Invalid license plate format!"
    )
    @Indexed(unique = true)
    private String licensePlate;

    @NotBlank(message = "Vehicle type is required!")
    private String vehicleType;

    @NotBlank(message = "Model is required!")
    private String model;

    @NotBlank(message = "Color is required!")
    private String color;

    @NotBlank(message = "Photo URL is required!")
    private String cabPhotoUrl;

    @NotBlank(message = "Description is required!")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters!")
    private String description;

    @Positive(message = "Cab fare must be a positive value!")
    @NotNull(message = "Cab fare is required!")
    private BigDecimal cabFare;

    @NotBlank(message = "Driver's name is required!")
    private String driverName;

    @DBRef
    private List<Booking> bookings = new ArrayList<>();
}
