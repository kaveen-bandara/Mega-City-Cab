package com.megaCityCab.backend.controller;

import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.service.connection.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllCustomers() {
        Response response = customerService.getAllCustomers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-id/{registrationNumber}")
    public ResponseEntity<Response> getCustomerById(@PathVariable("registrationNumber") String registrationNumber) {
        Response response = customerService.getCustomerById(registrationNumber);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{registrationNumber}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCustomer(@PathVariable("userId") String userId) {
        Response response = customerService.deleteCustomer(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
