package com.megaCityCab.backend.controller;

import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.Booking;
import com.megaCityCab.backend.service.connection.IBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @PostMapping("/book/{cabId}/{registrationNumber}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    public ResponseEntity<Response> saveBooking(
            @PathVariable String cabId,
            @PathVariable String registrationNumber,
            @Valid @RequestBody Booking bookingRequest) {
        Response response = bookingService.saveBooking(cabId, registrationNumber, bookingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings() {
        Response response = bookingService.getAllBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{confirmationCode}")
    public ResponseEntity<Response> findBookingByConfirmationCode(@PathVariable String confirmationCode) {
        Response response = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/cancel/{bookingNumber}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> cancelBooking(@PathVariable String bookingNumber) {
        Response response = bookingService.cancelBooking(bookingNumber);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
