package com.megaCityCab.backend.service.implementation;

import com.megaCityCab.backend.dto.UserDTO;
import com.megaCityCab.backend.dto.LoginRequest;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.User;
import com.megaCityCab.backend.exception.OurException;
import com.megaCityCab.backend.repository.UserRepository;
import com.megaCityCab.backend.service.connection.IUserService;
import com.megaCityCab.backend.utilities.JWTUtilities;
import com.megaCityCab.backend.utilities.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtilities jwtUtilities;
    private final AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {

        Response response = new Response();

        try {

            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("CUSTOMER");
            }

            if(userRepository.existsByEmail(user.getEmail())) {
                throw new OurException("Email already exists!");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);

            UserDTO userDTO = Utilities.mapUserEntityToUserDTO(savedUser);

            response.setStatusCode(200);
            response.setMessage("Successfully registered user!");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while registering user: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {

        Response response = new Response();

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new OurException("User not found!"));
            var token = jwtUtilities.generateToken(user);

            response.setStatusCode(200);
            response.setMessage("Successfully logged in user!");
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while logging in user: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllUsers() {

        Response response = new Response();

        try {
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utilities.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("Successfully gotten all users!");
            response.setUserList(userDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while getting all users: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {

        Response response = new Response();

        try {
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User not found!"));
            UserDTO userDTO = Utilities.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("Successfully gotten user by id!");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while getting user by id: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {

        Response response = new Response();

        try {
            userRepository.findById(userId).orElseThrow(()-> new OurException("User not found!"));
            userRepository.deleteById(userId);
            response.setStatusCode(200);
            response.setMessage("successfully deleted user!");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while deleting user: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getProfile(String email) {

        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(()-> new OurException("User not found!"));
            UserDTO userDTO = Utilities.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("Successfully gotten user information!");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage( e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while getting user information: " + e.getMessage());

        }
        return response;
    }

    public Response getUserBookingHistory(String userId) {

        Response response = new Response();

        try {
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User not found!"));
            UserDTO userDTO = Utilities.mapUserEntityToUserDTOPlusCustomerBookingsAndVehicle(user);
            response.setStatusCode(200);
            response.setMessage("Successfully gotten user booking history!");
            response.setUser(userDTO);

        } catch (OurException e){
            response.setStatusCode(404);
            response.setMessage( e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while getting user booking history: " + e.getMessage());

        }
        return response;
    }
}
