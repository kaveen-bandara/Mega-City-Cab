package com.megaCityCab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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

    @Indexed(unique = true)
    private String licensePlate;

    private String vehicleType;
    private String model;
    private String color;
    private String cabPhotoUrl;
    private String description;
    private BigDecimal cabFare;
    private Boolean inWork = false;

    @Indexed
    private String driverId = "Not Assigned";

    private List<String> bookingIds = new ArrayList<>();
}
