package com.megaCityCab.backend.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    @Indexed(unique = true)
    private String bookingNumber;

    @NotBlank(message = "Pickup location is required!")
    private String pickupLocation;

    @NotBlank(message = "Dropoff location is required!")
    private String dropoffLocation;

    @NotNull(message = "Pickup date is required!")
    private LocalDateTime pickupDate;

    @NotNull(message = "Pickup time is required!")
    private LocalDateTime pickupTime;

    private String message = "None";
    private String bookingConfirmationCode;

    @DBRef
    private Customer customer;

    @DBRef
    private Vehicle vehicle;

    @DBRef
    private Driver driver;
}
