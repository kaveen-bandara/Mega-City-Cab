package com.megaCityCab.backend.service.implementation;

import com.megaCityCab.backend.dto.AdminLoginRequest;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.exception.OurException;
import com.megaCityCab.backend.service.connection.IAdminService;
import com.megaCityCab.backend.utilities.JWTUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final JWTUtilities jwtUtilities;
    private final AuthenticationManager authenticationManager;

    @Override
    public Response login(AdminLoginRequest adminLoginRequest) {

        Response response = new Response();

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminLoginRequest.getUsername(), adminLoginRequest.getPassword()));
            var admin = adminRepository.findByUsername(adminLoginRequest.getUsername()).orElseThrow(()-> new OurException("Admin not found!"));
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
