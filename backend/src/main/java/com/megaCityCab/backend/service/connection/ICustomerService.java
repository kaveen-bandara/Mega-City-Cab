package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.CustomerLoginRequest;
import com.megaCityCab.backend.dto.Response;

public interface ICustomerService {

    Response register(Customer customer);

    Response login(CustomerLoginRequest customerLoginRequest);

    Response getAllCustomers();

    Response getCustomerById(String registrationNumber);

    Response deleteCustomer(String registrationNumber);

    Response getCustomerProfile(String email);

    Response getCustomerBookingHistory(String registrationNumber);
}
