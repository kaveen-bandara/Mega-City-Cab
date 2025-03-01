package com.megaCityCab.backend.service.implementation;

import com.megaCityCab.backend.dto.LoginRequest;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.exception.OurException;
import com.megaCityCab.backend.repository.AdminRepository;
import com.megaCityCab.backend.service.connection.IAdminService;
import com.megaCityCab.backend.utilities.JWTUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final JWTUtilities jwtUtilities;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AdminService(AdminRepository adminRepository, JWTUtilities jwtUtilities, AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.jwtUtilities = jwtUtilities;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Response login(LoginRequest loginRequest) {

        Response response = new Response();

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            var admin = adminRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new OurException("Admin not found!"));
            var token = jwtUtilities.generateToken(admin);

            response.setStatusCode(200);
            response.setMessage("Successfully logged in admin!");
            response.setToken(token);
            response.setRole("ADMIN");
            response.setExpirationTime("7 days");

        } catch(OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch(Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while logging in admin: " + e.getMessage());

        }
        return response;
    }
}
