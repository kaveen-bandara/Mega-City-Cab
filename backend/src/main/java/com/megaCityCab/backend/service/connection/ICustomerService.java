package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.LoginRequest;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.Customer;

public interface ICustomerService {

    Response register(Customer customer);

    Response login(LoginRequest loginRequest);

    Response getAllCustomers();

    Response getCustomerBookingHistory(String registrationNumber);

    Response deleteCustomer(String registrationNumber);

    Response getCustomerById(String registrationNumber);

    Response getMyInfo(String email);
}
