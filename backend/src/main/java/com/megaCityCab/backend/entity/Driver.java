package com.megaCityCab.backend.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "drivers")
public class Driver {

    @Id
    private String driverId;

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "^(07[01245678]\\d{7})$", message = "Invalid phone number format!")
    @Indexed(unique = true)
    private String phoneNumber;
}
