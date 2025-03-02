package com.megaCityCab.backend.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String bookingNumber;

    @NotBlank(message = "Pickup location is required!")
    private String pickupLocation;

    @NotBlank(message = "Dropoff location is required!")
    private String dropoffLocation;

    @NotNull(message = "Pickup date and time is required!")
    private LocalDateTime pickupDateTime;

    private String message = "None";
    private String bookingConfirmationCode;

    @DBRef
    private Customer customer;

    @DBRef
    private Vehicle vehicle;
}
