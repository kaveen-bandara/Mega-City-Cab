package com.megaCityCab.backend.entity;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "drivers")
public class Driver {

    @Id
    private String driverId;

    private String name;

    @Pattern(regexp = "^(07[01245678]\\d{7})$", message = "Invalid mobile number format!")
    private String phoneNumber;
}
