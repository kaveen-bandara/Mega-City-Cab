package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.AdminLoginRequest;
import com.megaCityCab.backend.dto.Response;

public interface IAdminService {

    Response login(AdminLoginRequest adminLoginRequest);
}
