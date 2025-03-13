package com.megaCityCab.backend.service.implementation;

import com.megaCityCab.backend.dto.CustomerDTO;
import com.megaCityCab.backend.dto.CustomerLoginRequest;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.exception.OurException;
import com.megaCityCab.backend.repository.CustomerRepository;
import com.megaCityCab.backend.service.connection.ICustomerService;
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
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtilities jwtUtilities;
    private final AuthenticationManager authenticationManager;

    @Override
    public Response register(Customer customer) {

        Response response = new Response();

        try {

            if(customerRepository.existsByEmail(customer.getEmail())) {
                throw new OurException("Email already exists!");
            }

            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            Customer savedCustomer = customerRepository.save(customer);

            CustomerDTO customerDTO = Utilities.mapCustomerEntityToCustomerDTO(savedCustomer);

            response.setStatusCode(200);
            response.setMessage("Successfully registered customer!");
            response.setCustomer(customerDTO);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while registering customer: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response login(CustomerLoginRequest customerLoginRequest) {

        Response response = new Response();

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerLoginRequest.getEmail(), customerLoginRequest.getPassword()));
            var customer = customerRepository.findByEmail(customerLoginRequest.getEmail()).orElseThrow(()-> new OurException("Customer not found!"));
            var token = jwtUtilities.generateToken(customer);

            response.setStatusCode(200);
            response.setMessage("Successfully logged in customer!");
            response.setToken(token);
            response.setRole("CUSTOMER");
            response.setExpirationTime("7 days");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while logging in customer: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllCustomers() {

        Response response = new Response();

        try {
            List<Customer> customerList = customerRepository.findAll();
            List<CustomerDTO> customerDTOList = Utilities.mapCustomerListEntityToCustomerListDTO(customerList);
            response.setStatusCode(200);
            response.setMessage("Successfully gotten all customers!");
            response.setCustomerList(customerDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while getting all customers: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getCustomerById(String registrationNumber) {

        Response response = new Response();

        try {
            Customer customer = customerRepository.findById(registrationNumber).orElseThrow(()-> new OurException("Customer not found!"));
            CustomerDTO customerDTO = Utilities.mapCustomerEntityToCustomerDTO(customer);

            response.setStatusCode(200);
            response.setMessage("Successfully gotten user by id!");
            response.setCustomer(customerDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while getting customer by id: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response deleteCustomer(String registrationNumber) {

        Response response = new Response();

        try {
            customerRepository.findById(registrationNumber).orElseThrow(()-> new OurException("Customer not found!"));
            customerRepository.deleteById(registrationNumber);
            response.setStatusCode(200);
            response.setMessage("successfully deleted customer!");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while deleting customer: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getCustomerProfile(String email) {

        Response response = new Response();

        try {
            Customer customer = customerRepository.findByEmail(email).orElseThrow(()-> new OurException("Customer not found!"));
            CustomerDTO customerDTO = Utilities.mapCustomerEntityToCustomerDTO(customer);

            response.setStatusCode(200);
            response.setMessage("Successfully gotten customer information!");
            response.setCustomer(customerDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage( e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while getting customer information: " + e.getMessage());

        }
        return response;
    }

    public Response getCustomerBookingHistory(String registrationNumber) {

        Response response = new Response();

        try {
            Customer customer = customerRepository.findById(registrationNumber).orElseThrow(()-> new OurException("Customer not found!"));
            CustomerDTO customerDTO = Utilities.mapCustomerEntityToCustomerDTOPlusCustomerBookingsAndVehicle(customer);
            response.setStatusCode(200);
            response.setMessage("Successfully gotten customer booking history!");
            response.setCustomer(customerDTO);

        } catch (OurException e){
            response.setStatusCode(404);
            response.setMessage( e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error while getting customer booking history: " + e.getMessage());

        }
        return response;
    }
}
