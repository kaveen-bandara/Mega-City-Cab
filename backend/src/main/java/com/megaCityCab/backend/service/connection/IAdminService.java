package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.LoginRequest;
import com.megaCityCab.backend.dto.Response;

public interface IAdminService {

    Response login(LoginRequest loginRequest);
}
