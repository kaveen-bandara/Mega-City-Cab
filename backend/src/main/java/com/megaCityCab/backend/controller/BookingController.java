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

    @PostMapping("/book/{vehicleId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    public ResponseEntity<Response> saveBooking(
            @PathVariable String vehicleId,
            @PathVariable String userId,
            @Valid @RequestBody Booking bookingRequest) {
        Response response = bookingService.saveBooking(vehicleId, userId, bookingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings() {
        Response response = bookingService.getAllBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{confirmationCode}")
    public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        Response response = bookingService.getBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> cancelBooking(@PathVariable String bookingId) {
        Response response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
