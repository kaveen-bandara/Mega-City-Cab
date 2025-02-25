package com.megaCityCab.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO {

    private String driverId;
    private String name;
    private String phoneNumber;
}
