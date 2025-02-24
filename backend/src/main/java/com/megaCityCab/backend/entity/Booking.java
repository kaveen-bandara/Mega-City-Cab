package com.megaCityCab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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

    private String pickupLocation;
    private String dropoffLocation;
    private LocalDateTime pickupTime;
    private String message = "None";
    private String bookingConfirmationCode;

    private Customer customer;
    private Vehicle vehicle;
    private Driver driver;
}
