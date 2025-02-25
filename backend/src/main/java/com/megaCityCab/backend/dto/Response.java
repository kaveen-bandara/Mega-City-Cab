package com.megaCityCab.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;

    private CustomerDTO customer;
    private VehicleDTO vehicle;
    private DriverDTO driver;
    private BookingDTO booking;
    private List<CustomerDTO> customerList;
    private List<VehicleDTO> vehicleList;
    private List<DriverDTO> driverList;
    private List<BookingDTO> bookingList;
}
