package com.megaCityCab.backend.controller;

import com.megaCityCab.backend.dto.AdminLoginRequest;
import com.megaCityCab.backend.dto.CustomerLoginRequest;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.Customer;
import com.megaCityCab.backend.service.connection.IAdminService;
import com.megaCityCab.backend.service.connection.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IAdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody Customer customer) {
        Response response = customerService.register(customer);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody CustomerLoginRequest customerLoginRequest) {
        Response response = customerService.login(customerLoginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<Response> loginAdmin(@Valid @RequestBody AdminLoginRequest adminLoginRequest) {
        Response response = adminService.login(adminLoginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
