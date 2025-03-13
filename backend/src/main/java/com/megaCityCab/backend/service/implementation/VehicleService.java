package com.megaCityCab.backend.service.implementation;

import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.dto.VehicleDTO;
import com.megaCityCab.backend.entity.Booking;
import com.megaCityCab.backend.entity.Vehicle;
import com.megaCityCab.backend.exception.OurException;
import com.megaCityCab.backend.repository.BookingRepository;
import com.megaCityCab.backend.repository.VehicleRepository;
import com.megaCityCab.backend.service.AWSS3Service;
import com.megaCityCab.backend.service.connection.IVehicleService;
import com.megaCityCab.backend.utilities.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService implements IVehicleService {

    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;
    private final AWSS3Service awsS3Service;

    @Override
    public Response addNewVehicle(MultipartFile photo, String licensePlate, String vehicleType, String model, String color, String description, BigDecimal fare, String driverName) {

        Response response = new Response();

        try {
            String imageUrl = awsS3Service.saveImageToS3(photo);
            Vehicle vehicle = new Vehicle();
            vehicle.setVehiclePhotoUrl(imageUrl);
            vehicle.setLicensePlate(licensePlate);
            vehicle.setVehicleType(vehicleType);
            vehicle.setModel(model);
            vehicle.setColor(color);
            vehicle.setDescription(description);
            vehicle.setFare(fare);
            vehicle.setDriverName(driverName);

            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            VehicleDTO vehicleDTO = Utilities.mapVehicleEntityToVehicleDTO(savedVehicle);

            response.setStatusCode(200);
            response.setMessage("Successfully added vehicle!");
            response.setVehicle(vehicleDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while adding vehicle: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllVehicleTypes() {
        return vehicleRepository.findDistinctVehicleType();
    }

    @Override
    public Response getAllVehicles() {

        Response response = new Response();

        try {
            List<Vehicle> vehicleList = vehicleRepository.findAll();
            List<VehicleDTO> vehicleDTOList = Utilities.mapVehicleListEntityToVehicleListDTO(vehicleList);

            response.setStatusCode(200);
            response.setMessage("Successfully gotten all vehicles!");
            response.setVehicleList(vehicleDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting all vehicles: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getVehicleById(String vehicleId) {

        Response response = new Response();

        try {
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()-> new OurException("Vehicle not found!"));
            VehicleDTO vehicleDTO = Utilities.mapVehicleEntityToVehicleDTOPlusBookings(vehicle);

            response.setStatusCode(200);
            response.setMessage("Successfully gotten vehicle by id!");
            response.setVehicle(vehicleDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting vehicle by id: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteVehicle(String cabId) {

        Response response = new Response();

        try {
            vehicleRepository.findById(cabId).orElseThrow(() -> new OurException("Vehicle not found!"));
            vehicleRepository.deleteById(cabId);

            response.setStatusCode(200);
            response.setMessage("Successfully deleted vehicle!");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting vehicle: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateVehicleDetails(String vehicleId, String licensePlate, String vehicleType, String model, String color, String description, BigDecimal fare, String driverName, MultipartFile photo) {

        Response response = new Response();

        try {
            String imageUrl = null;
            if (photo != null && !photo.isEmpty()){
                imageUrl = awsS3Service.saveImageToS3(photo);
            }

            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()-> new OurException("Vehicle not found!"));

            if (licensePlate != null && !licensePlate.isBlank()) vehicle.setLicensePlate(licensePlate);
            if (vehicleType != null && !vehicleType.isBlank()) vehicle.setVehicleType(vehicleType);
            if (model != null && !model.isBlank()) vehicle.setModel(model);
            if (color != null && !color.isBlank()) vehicle.setColor(color);
            if (description != null && !description.isBlank()) vehicle.setDescription(description);
            if (fare != null) vehicle.setFare(fare);
            if (driverName != null && !driverName.isBlank()) vehicle.setDriverName(driverName);
            if(imageUrl != null) vehicle.setVehiclePhotoUrl(imageUrl);

            Vehicle updatedVehicle = vehicleRepository.save(vehicle);
            VehicleDTO vehicleDTO = Utilities.mapVehicleEntityToVehicleDTO(updatedVehicle);

            response.setStatusCode(200);
            response.setMessage("Successfully updated vehicle details!");
            response.setVehicle(vehicleDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while updating vehicle details: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableVehicles() {

        Response response = new Response();

        try {
            List<Vehicle> vehicleList = vehicleRepository.findAllAvailableVehicles();
            List<VehicleDTO> vehicleDTOList = Utilities.mapVehicleListEntityToVehicleListDTO(vehicleList);

            response.setStatusCode(200);
            response.setMessage("Successfully gotten all available vehicles!");
            response.setVehicleList(vehicleDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting all available vehicles: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableVehiclesByDateAndVehicleType(LocalDate startDate, LocalDate endDate, String vehicleType) {

        Response response = new Response();

        try {
            List<Booking> bookings = bookingRepository.findBookingsByDateRange(startDate, endDate, vehicleType);
            List<String> bookedVehicleIds = bookings.stream().map(booking -> booking.getVehicle().getId()).toList();

            List<Vehicle> availableVehicles = vehicleRepository.findByVehicleTypeLikeAndCabIdNotIn(vehicleType, bookedVehicleIds);
            List<VehicleDTO> vehicleDTOList = Utilities.mapVehicleListEntityToVehicleListDTO(availableVehicles);

            response.setStatusCode(200);
            response.setMessage("Successfully gotten all available vehicles!");
            response.setVehicleList(vehicleDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting available vehicles: " + e.getMessage());
        }
        return response;
    }
}
