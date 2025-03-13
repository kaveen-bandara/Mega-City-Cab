package com.megaCityCab.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String id;
    private String name;
    private String email;
    private String mobileNumber;
    private String role;

    private List<BookingDTO> bookings = new ArrayList<>();
}
