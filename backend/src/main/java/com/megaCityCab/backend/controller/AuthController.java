package com.megaCityCab.backend.controller;

import com.megaCityCab.backend.dto.LoginRequest;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.Customer;
import com.megaCityCab.backend.service.connection.IAdminService;
import com.megaCityCab.backend.service.connection.ICustomerService;
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
    public ResponseEntity<Response> register(@RequestBody Customer customer) {
        Response response = customerService.register(customer);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = customerService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<Response> loginAdmin(@RequestBody LoginRequest loginRequest) {
        Response response = adminService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
