package com.megaCityCab.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    private String registrationNumber;
    private String firstName;
    private String lastName;
    private String address;
    private String NIC;
    private String mobileNumber;
    private String email;

    private List<BookingDTO> bookings = new ArrayList<>();
}
