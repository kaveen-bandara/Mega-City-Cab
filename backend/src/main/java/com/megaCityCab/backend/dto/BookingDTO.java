package com.megaCityCab.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    private String bookingNumber;
    private String pickupLocation;
    private String dropoffLocation;
    private LocalDateTime pickupDateTime;
    private String message;
    private String confirmationCode;

    private CustomerDTO customer;
    private VehicleDTO vehicle;
}
