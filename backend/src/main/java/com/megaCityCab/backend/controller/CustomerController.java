package com.megaCityCab.backend.controller;

import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.service.connection.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllCustomers() {
        Response response = customerService.getAllCustomers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{registrationNumber}")
    public ResponseEntity<Response> getCustomerById(@PathVariable("registrationNumber") String registrationNumber) {
        Response response = customerService.getCustomerById(registrationNumber);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{registrationNumber}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCustomer(@PathVariable("registrationNumber") String registrationNumber) {
        Response response = customerService.deleteCustomer(registrationNumber);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<Response> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = customerService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/bookings/{registrationNumber}")
    public ResponseEntity<Response> getCustomerBookingHistory(@PathVariable("registrationNumber") String registrationNumber) {
        Response response = customerService.getCustomerBookingHistory(registrationNumber);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
