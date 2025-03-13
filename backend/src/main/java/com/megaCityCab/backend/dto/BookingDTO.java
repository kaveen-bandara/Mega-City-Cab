package com.megaCityCab.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    private String id;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private String message;
    private String confirmationCode;

    private UserDTO user;
    private VehicleDTO vehicle;
}
