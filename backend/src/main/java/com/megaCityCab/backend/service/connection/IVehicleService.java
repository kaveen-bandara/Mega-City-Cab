package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.Vehicle;

public interface IVehicleService {

    Response addNewVehicle(Vehicle vehicle);
    Response getAllVehicles();
    Response getVehicleById(String cabId);
    Response deleteVehicle(String cabId);
    Response updateVehicleDetails();
    Response getAllAvailableVehicles();

}
