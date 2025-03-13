package com.megaCityCab.backend.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    @NotBlank(message = "Destination is required!")
    private String destination;

    @NotNull(message = "Start date is required!")
    private LocalDate startDate;

    @NotNull(message = "End date is required!")
    private LocalDate endDate;

    private String message;

    private String confirmationCode;

    @DBRef
    private User user;

    @DBRef
    private Vehicle vehicle;

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", destination='" + destination + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", message='" + message + '\'' +
                ", confirmationCode='" + confirmationCode + '\'' +
                '}';
    }
}
