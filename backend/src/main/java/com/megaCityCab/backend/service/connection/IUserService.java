package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.LoginRequest;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.User;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserById(String userId);

    Response deleteUser(String userId);

    Response getUserProfile(String email);

    Response getUserBookingHistory(String userId);
}
